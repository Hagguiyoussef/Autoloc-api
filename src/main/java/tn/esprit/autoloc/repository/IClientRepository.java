package tn.esprit.autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.domain.Client;

public interface IClientRepository extends JpaRepository<Client, Long> {

    boolean existsByTelephone(String telephone);

    boolean existsByTelephoneAndIdClientNot(String telephone, Long idClient);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCaseAndIdClientNot(String email, Long idClient);

    boolean existsByNumPermis(String numPermis);

    boolean existsByNumPermisAndIdClientNot(String numPermis, Long idClient);
}