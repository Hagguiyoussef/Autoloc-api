package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.exception.DuplicateResourceException;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IClientRepository;
import tn.esprit.autoloc.web.dto.ClientRequestDto;
import tn.esprit.autoloc.web.dto.ClientResponseDto;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {

    private final IClientRepository clientRepository;
    private final IClientMapper clientMapper;

    @Override
    public ClientResponseDto create(ClientRequestDto dto) {
        verifierUnicite(dto);
        Client client = clientMapper.toEntity(dto);
        return clientMapper.toResponse(clientRepository.save(client));
    }

    @Override
    public ClientResponseDto findById(Long id) {
        return clientRepository.findById(id)
                .map(clientMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Client", id));
    }

    private void verifierUnicite(ClientRequestDto dto) {
        if (dto.email() != null && !dto.email().isBlank()
                && clientRepository.existsByEmailIgnoreCase(dto.email())) {
            throw new DuplicateResourceException("Cet email est deja utilise par un autre client");
        }
        if (dto.telephone() != null && !dto.telephone().isBlank()
                && clientRepository.existsByTelephone(dto.telephone())) {
            throw new DuplicateResourceException("Ce numero de telephone est deja utilise par un autre client");
        }
    }
}