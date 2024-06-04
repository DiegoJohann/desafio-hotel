package br.com.johann.desafiohotel.domain.repositories;

import br.com.johann.desafiohotel.domain.entities.Hospede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HospedeRepository extends JpaRepository<Hospede, Long>, JpaSpecificationExecutor<Hospede> {

}