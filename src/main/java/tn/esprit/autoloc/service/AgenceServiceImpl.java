package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.exception.DuplicateResourceException;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IAgenceRepository;

@Service
@RequiredArgsConstructor
public class AgenceServiceImpl implements IAgenceService {

    private final IAgenceRepository agenceRepository;

    @Override
    public Agence create(Agence entite) {
        if (entite.getIdAgence() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau agence ne doit pas avoir d'identifiant");
        }
        verifier(entite);
        verifierUnicite(entite, null);
        return agenceRepository.save(entite);
    }

    @Override
    public Agence findById(Long id) {
        return agenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", id));
    }

    @Override
    public List<Agence> findAll() {
        return agenceRepository.findAll();
    }

    @Override
    public Agence update(Long id, Agence entite) {
        verifier(entite);
        Agence existant = findById(id);
        verifierUnicite(entite, id);
        existant.setNom(entite.getNom());
        existant.setVille(entite.getVille());
        existant.setAdresse(entite.getAdresse());
        existant.setTelephone(entite.getTelephone());
        return agenceRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!agenceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Agence", id);
        }
        agenceRepository.deleteById(id);
    }

    private void verifier(Agence entite) {
        if (entite.getNom() == null || entite.getNom().isBlank()
                || entite.getVille() == null || entite.getVille().isBlank()
                || entite.getAdresse() == null || entite.getAdresse().isBlank()) {
            throw new IllegalArgumentException("Le nom, la ville et l'adresse de l'agence sont obligatoires");
        }
    }

    private void verifierUnicite(Agence entite, Long id) {
        String telephone = entite.getTelephone();
        if (telephone != null && !telephone.isBlank()) {
            boolean pris = id == null
                    ? agenceRepository.existsByTelephone(telephone)
                    : agenceRepository.existsByTelephoneAndIdAgenceNot(telephone, id);
            if (pris) {
                throw new DuplicateResourceException("Ce numero de telephone est deja utilise par une autre agence");
            }
        }
        String adresse = entite.getAdresse();
        boolean adressePrise = id == null
                ? agenceRepository.existsByAdresseIgnoreCase(adresse)
                : agenceRepository.existsByAdresseIgnoreCaseAndIdAgenceNot(adresse, id);
        if (adressePrise) {
            throw new DuplicateResourceException("Cette adresse est deja utilisee par une autre agence");
        }
    }
}