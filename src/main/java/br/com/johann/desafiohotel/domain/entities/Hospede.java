package br.com.johann.desafiohotel.domain.entities;

import br.com.johann.desafiohotel.domain.entities.dto.HospedeDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "hospede")
public class Hospede implements Serializable {

    @Serial
    private static final long serialVersionUID = 11688735451L;

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String documento;

    @Column(nullable = false)
    private String telefone;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hospede")
    private List<CheckIn> checkIns;

    public Hospede(HospedeDto dto) {
        this.nome = dto.getNome();
        this.documento = dto.getDocumento();
        this.telefone = dto.getTelefone();
    }
}
