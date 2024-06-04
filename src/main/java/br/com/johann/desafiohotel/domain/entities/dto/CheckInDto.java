package br.com.johann.desafiohotel.domain.entities.dto;

import br.com.johann.desafiohotel.domain.entities.CheckIn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckInDto implements Serializable {

    private Long id;
    private Long hospedeId;
    private LocalDateTime dataEntrada;
    private LocalDateTime dataSaida;
    private boolean adicionalVeiculo;

    public CheckInDto(CheckIn checkIn) {
        this.id = checkIn.getId();
        this.hospedeId = checkIn.getHospede().getId();
        this.dataEntrada = checkIn.getDataEntrada();
        this.dataSaida = checkIn.getDataSaida();
        this.adicionalVeiculo = checkIn.isAdicionalVeiculo();
    }
}
