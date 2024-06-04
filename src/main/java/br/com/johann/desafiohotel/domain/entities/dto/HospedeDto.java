package br.com.johann.desafiohotel.domain.entities.dto;

import br.com.johann.desafiohotel.domain.entities.Hospede;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class HospedeDto implements Serializable {

    private Long id;
    private String nome;
    private String documento;
    private String telefone;

    public HospedeDto(Hospede hospede) {
        this.id = hospede.getId();
        this.nome = hospede.getNome();
        this.documento = hospede.getDocumento();
        this.telefone = hospede.getTelefone();
    }
}
