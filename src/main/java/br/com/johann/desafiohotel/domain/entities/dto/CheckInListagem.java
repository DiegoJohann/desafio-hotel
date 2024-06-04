package br.com.johann.desafiohotel.domain.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckInListagem implements Serializable {

    private Long idHospede;
    private String nomeHospede;
    private String documento;
    private Double valorDevido;
    private Double valorGasto;
}
