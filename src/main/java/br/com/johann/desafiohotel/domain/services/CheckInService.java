package br.com.johann.desafiohotel.domain.services;

import br.com.johann.desafiohotel.domain.entities.CheckIn;
import br.com.johann.desafiohotel.domain.entities.dto.CheckInDto;
import br.com.johann.desafiohotel.domain.entities.dto.CheckInListagem;
import br.com.johann.desafiohotel.domain.entities.dto.HospedeDto;
import br.com.johann.desafiohotel.domain.repositories.CheckinRepository;
import br.com.johann.desafiohotel.domain.repositories.HospedeRepository;
import br.com.johann.desafiohotel.domain.services.exceptions.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class CheckInService {

    private static final double VALOR_SEMANA = 120.00;
    private static final double VALOR_FIM_DE_SEMANA = 150.00;
    private static final double VALOR_GARAGEM_SEMANA = 15.00;
    private static final double VALOR_GARAGEM_FIM_DE_SEMANA = 20.00;

    private final CheckinRepository checkinRepository;
    private final HospedeRepository hospedeRepository;

    public CheckInService(CheckinRepository checkinRepository, HospedeRepository hospedeRepository) {
        this.checkinRepository = checkinRepository;
        this.hospedeRepository = hospedeRepository;
    }

    @Transactional
    public CheckInDto novoCheckIn(CheckInDto dto) {
        Optional<CheckIn> checkInPendente = this.checkinRepository.findByHospedeAndDataSaidaIsNull(this.hospedeRepository.findById(dto.getHospedeId()).orElseThrow(() -> new HospedeNaoEncontradoException("Hóspede não encontrado.")));
        if (checkInPendente.isPresent()) {
            throw new CheckInPendenteException("Já existe um check-in pendente para esse hóspede!");
        } else {
            CheckIn novoCheckIn = new CheckIn();
            novoCheckIn.setHospede(this.hospedeRepository.findById(dto.getHospedeId()).orElseThrow(() -> new HospedeNaoEncontradoException("Hóspede não encontrado.")));
            novoCheckIn.setDataEntrada(dto.getDataEntrada());
            novoCheckIn.setAdicionalVeiculo(dto.isAdicionalVeiculo());
            return new CheckInDto(this.checkinRepository.save(novoCheckIn));
        }
    }

    private List<CheckInDto> buscaTodos() {
        return this.checkinRepository.findAll().stream().map(CheckInDto::new).toList();
    }

    private List<CheckInDto> buscaComDataSaida() {
        return this.checkinRepository.findByDataSaidaIsNotNull().stream().map(CheckInDto::new).toList();
    }

    private List<CheckInDto> buscaSemDataSaida() {
        return this.checkinRepository.findByDataSaidaIsNull().stream().map(CheckInDto::new).toList();
    }

    @Transactional
    public CheckInDto realizarCheckout(CheckInDto dto) {
        if (dto.getDataSaida() != null) {
            CheckIn checkIn = this.checkinRepository.findById(dto.getId()).orElseThrow(() -> new CheckInNaoEncontradoException("Check-in não encontrado."));
            if (ChronoUnit.DAYS.between(checkIn.getDataEntrada(), dto.getDataSaida()) >= 1) {
                checkIn.setDataSaida(dto.getDataSaida());
                return new CheckInDto(this.checkinRepository.save(checkIn));
            } else {
                throw new CheckoutInvalidoException("A data de saída deve ser pelo menos um dia maior do que a entrada!");
            }
        } else {
            throw new DataSaidaNullException("A data de saída não pode estar em branco para o checkout!");
        }
    }

    public List<CheckInListagem> listaCheckIns(String filtro) {
        List<CheckInDto> checkInDtos = switch (filtro) {
            case "" -> this.buscaTodos();
            case "comDataSaida" -> this.buscaComDataSaida();
            case "semDataSaida" -> this.buscaSemDataSaida();
            default -> throw new FiltroDesconhecidoException("Filtro desconhecido");
        };

        Map<Long, CheckInListagem> mapCheckIns = new HashMap<>();
        for (CheckInDto checkIn : checkInDtos) {
            HospedeDto hospedeDto = new HospedeDto(this.hospedeRepository.findById(checkIn.getHospedeId()).orElseThrow(() -> new HospedeNaoEncontradoException("Hóspede não encontrado.")));
            CheckInListagem listagemObj = mapCheckIns.getOrDefault(checkIn.getHospedeId(), new CheckInListagem(hospedeDto.getId(), hospedeDto.getNome(), hospedeDto.getDocumento(), 0.0, 0.0));

            if (checkIn.getDataSaida() == null) {
                listagemObj.setValorDevido(listagemObj.getValorDevido() + calcularValorDevido(checkIn));
            } else {
                listagemObj.setValorGasto(listagemObj.getValorGasto() + calcularValorGasto(checkIn));
            }

            mapCheckIns.put(hospedeDto.getId(), listagemObj);
        }

        return new ArrayList<>(mapCheckIns.values());
    }

    private static Double calcularValorDevido(CheckInDto dto) {
        return calcularValorGasto(new CheckInDto(dto.getId(), dto.getHospedeId(), dto.getDataEntrada(), LocalDateTime.now(), dto.isAdicionalVeiculo()));
    }

    private static Double calcularValorGasto(CheckInDto dto) {
        LocalDateTime entrada = dto.getDataEntrada();
        LocalDateTime saida = dto.getDataSaida();
        long dias = ChronoUnit.DAYS.between(entrada.toLocalDate(), saida.toLocalDate()) + 1;

        double valorTotal = 0.0;

        for (int i = 0; i < dias; i++) {
            LocalDateTime dataAtual = entrada.plusDays(i);
            DayOfWeek diaSemana = dataAtual.getDayOfWeek();

            if (diaSemana == DayOfWeek.SATURDAY || diaSemana == DayOfWeek.SUNDAY) {
                valorTotal += VALOR_FIM_DE_SEMANA;
                if (dto.isAdicionalVeiculo()) {
                    valorTotal += VALOR_GARAGEM_FIM_DE_SEMANA;
                }
            } else {
                valorTotal += VALOR_SEMANA;
                if (dto.isAdicionalVeiculo()) {
                    valorTotal += VALOR_GARAGEM_SEMANA;
                }
            }
        }

        if (saida.getHour() >= 16 && saida.getMinute() > 30) {
            valorTotal += entrada.getDayOfWeek() == DayOfWeek.SATURDAY || entrada.getDayOfWeek() == DayOfWeek.SUNDAY ? VALOR_FIM_DE_SEMANA : VALOR_SEMANA;
            if (dto.isAdicionalVeiculo()) {
                valorTotal += entrada.getDayOfWeek() == DayOfWeek.SATURDAY || entrada.getDayOfWeek() == DayOfWeek.SUNDAY ? VALOR_GARAGEM_FIM_DE_SEMANA : VALOR_GARAGEM_SEMANA;
            }
        }

        return valorTotal;
    }
}
