package br.com.johann.desafiohotel.domain.repositories;

import br.com.johann.desafiohotel.domain.entities.CheckIn;
import br.com.johann.desafiohotel.domain.entities.Hospede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface CheckinRepository extends JpaRepository<CheckIn, Long>, JpaSpecificationExecutor<CheckIn> {

    List<CheckIn> findByDataSaidaIsNotNull();

    List<CheckIn> findByDataSaidaIsNull();

    Optional<CheckIn> findByHospedeAndDataSaidaIsNull(final Hospede hospede);
}