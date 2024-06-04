package br.com.johann.desafiohotel.domain.services;

import br.com.johann.desafiohotel.domain.entities.Hospede;
import br.com.johann.desafiohotel.domain.entities.dto.HospedeDto;
import br.com.johann.desafiohotel.domain.repositories.HospedeRepository;
import br.com.johann.desafiohotel.domain.services.exceptions.HospedeNaoEncontradoException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HospedeService {

    private final HospedeRepository hospedeRepository;

    public HospedeService(HospedeRepository hospedeRepository) {
        this.hospedeRepository = hospedeRepository;
    }

    @Transactional
    public HospedeDto novoHospede(HospedeDto dto) {
        Hospede novoHospede = new Hospede(dto);
        return new HospedeDto(this.hospedeRepository.save(novoHospede));
    }

    public List<HospedeDto> listarTodos() {
        return this.hospedeRepository.findAll().stream().map(HospedeDto::new).toList();
    }

    public HospedeDto buscaPorId(Long hospedeId) {
        return this.hospedeRepository.findById(hospedeId).map(HospedeDto::new).orElseThrow(() -> new HospedeNaoEncontradoException("Hóspede não encontrado"));
    }
}
