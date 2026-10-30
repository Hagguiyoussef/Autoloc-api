package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.DuplicateResourceException;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.repository.IEquipementRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;
    private final IAgenceRepository agenceRepository;
    private final IEquipementRepository equipementRepository;

    @Override
    public Vehicule create(Vehicule entite) {
        if (entite.getIdVehicule() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau vehicule ne doit pas avoir d'identifiant");
        }
        verifier(entite);
        verifierUnicite(entite, null);
        return vehiculeRepository.save(entite);
    }

    @Override
    public Vehicule findById(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
    }

    @Override
    public List<Vehicule> findAll() {
        return vehiculeRepository.findAll();
    }

    @Override
    public Vehicule update(Long id, Vehicule entite) {
        verifier(entite);
        Vehicule existant = findById(id);
        verifierUnicite(entite, id);
        existant.setImmatriculation(entite.getImmatriculation());
        existant.setMarque(entite.getMarque());
        existant.setModele(entite.getModele());
        existant.setCategorie(entite.getCategorie());
        existant.setTarifJournalier(entite.getTarifJournalier());
        existant.setStatut(entite.getStatut());
        return vehiculeRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!vehiculeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicule", id);
        }
        vehiculeRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Vehicule affecterVehicule(Long idVehicule, Long idAgence) {
        Vehicule vehicule = findById(idVehicule);
        Agence agence = agenceRepository.findById(idAgence)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", idAgence));
        if (vehicule.getAgence() != null && vehicule.getAgence().getIdAgence().equals(idAgence)) {
            throw new IllegalArgumentException("Ce vehicule est deja affecte a cette agence");
        }
        vehicule.setAgence(agence);
        return vehiculeRepository.save(vehicule);
    }

    @Override
    @Transactional
    public Vehicule affecterEquipement(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = findById(idVehicule);
        Equipement equipement = equipementRepository.findById(idEquipement)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", idEquipement));
        boolean dejaInstalle = vehicule.getEquipements().stream()
                .anyMatch(e -> e.getIdEquipement().equals(idEquipement));
        if (dejaInstalle) {
            throw new IllegalArgumentException("Cet equipement est deja installe sur ce vehicule");
        }
        vehicule.getEquipements().add(equipement);
        return vehiculeRepository.save(vehicule);
    }

    @Override
    @Transactional
    public Vehicule desaffecterEquipement(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = findById(idVehicule);
        if (!equipementRepository.existsById(idEquipement)) {
            throw new ResourceNotFoundException("Equipement", idEquipement);
        }
        boolean retire = vehicule.getEquipements()
                .removeIf(e -> e.getIdEquipement().equals(idEquipement));
        if (!retire) {
            throw new IllegalArgumentException("Cet equipement n'est pas installe sur ce vehicule");
        }
        return vehiculeRepository.save(vehicule);
    }

    private void verifier(Vehicule entite) {
        if (entite.getTarifJournalier() == null || entite.getTarifJournalier().signum() < 0) {
            throw new IllegalArgumentException("Le tarif journalier ne peut pas etre negatif");
        }
    }

    private void verifierUnicite(Vehicule entite, Long id) {
        String immatriculation = entite.getImmatriculation();
        if (immatriculation != null && !immatriculation.isBlank()) {
            boolean prise = id == null
                    ? vehiculeRepository.existsByImmatriculationIgnoreCase(immatriculation)
                    : vehiculeRepository.existsByImmatriculationIgnoreCaseAndIdVehiculeNot(immatriculation, id);
            if (prise) {
                throw new DuplicateResourceException("Cette immatriculation est deja utilisee par un autre vehicule");
            }
        }
    }
}