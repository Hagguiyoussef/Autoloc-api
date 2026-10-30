package tn.esprit.autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.domain.Agence;

public interface IAgenceRepository extends JpaRepository<Agence, Long> {

    boolean existsByTelephone(String telephone);

    boolean existsByTelephoneAndIdAgenceNot(String telephone, Long idAgence);

    boolean existsByAdresseIgnoreCase(String adresse);

    boolean existsByAdresseIgnoreCaseAndIdAgenceNot(String adresse, Long idAgence);
}