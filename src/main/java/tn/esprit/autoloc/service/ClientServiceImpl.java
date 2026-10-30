package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.exception.DuplicateResourceException;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IClientRepository;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {

    private final IClientRepository clientRepository;

    @Override
    public Client create(Client entite) {
        if (entite.getIdClient() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau client ne doit pas avoir d'identifiant");
        }
        verifier(entite);
        verifierUnicite(entite, null);
        return clientRepository.save(entite);
    }

    @Override
    public Client findById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client", id));
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client update(Long id, Client entite) {
        verifier(entite);
        Client existant = findById(id);
        verifierUnicite(entite, id);
        existant.setNom(entite.getNom());
        existant.setPrenom(entite.getPrenom());
        existant.setEmail(entite.getEmail());
        existant.setTelephone(entite.getTelephone());
        existant.setNumPermis(entite.getNumPermis());
        existant.setDateInscription(entite.getDateInscription());
        return clientRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Client", id);
        }
        clientRepository.deleteById(id);
    }

    private void verifier(Client entite) {
        if (entite.getNom() == null || entite.getNom().isBlank()
                || entite.getPrenom() == null || entite.getPrenom().isBlank()
                || entite.getEmail() == null || entite.getEmail().isBlank()) {
            throw new IllegalArgumentException("Le nom, le prenom et l'email du client sont obligatoires");
        }
    }

    private void verifierUnicite(Client entite, Long id) {
        String email = entite.getEmail();
        boolean emailPris = id == null
                ? clientRepository.existsByEmailIgnoreCase(email)
                : clientRepository.existsByEmailIgnoreCaseAndIdClientNot(email, id);
        if (emailPris) {
            throw new DuplicateResourceException("Cet email est deja utilise par un autre client");
        }
        String telephone = entite.getTelephone();
        if (telephone != null && !telephone.isBlank()) {
            boolean pris = id == null
                    ? clientRepository.existsByTelephone(telephone)
                    : clientRepository.existsByTelephoneAndIdClientNot(telephone, id);
            if (pris) {
                throw new DuplicateResourceException("Ce numero de telephone est deja utilise par un autre client");
            }
        }
        String permis = entite.getNumPermis();
        if (permis != null && !permis.isBlank()) {
            boolean pris = id == null
                    ? clientRepository.existsByNumPermis(permis)
                    : clientRepository.existsByNumPermisAndIdClientNot(permis, id);
            if (pris) {
                throw new DuplicateResourceException("Ce numero de permis est deja utilise par un autre client");
            }
        }
    }
}