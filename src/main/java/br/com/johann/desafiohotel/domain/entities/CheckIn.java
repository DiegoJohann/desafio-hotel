package br.com.johann.desafiohotel.domain.entities;

import br.com.johann.desafiohotel.domain.entities.dto.CheckInDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "checkin")
public class CheckIn implements Serializable {

    @Serial
    private static final long serialVersionUID = 11543487526L;

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hospede_id", nullable = false)
    private Hospede hospede;

    @Column(name = "data_entrada", nullable = false)
    private LocalDateTime dataEntrada;

    @Column(name = "data_saida")
    private LocalDateTime dataSaida;

    @Column(name = "adicional_veiculo", columnDefinition = "boolean default false")
    private boolean adicionalVeiculo;
}
