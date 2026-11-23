package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IClientRepository;
import tn.esprit.autoloc.repository.IReservationRepository;
import tn.esprit.autoloc.web.dto.ReservationResponseDto;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository reservationRepository;
    private final IClientRepository clientRepository;
    private final IReservationMapper reservationMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponseDto> findAll() {
        return reservationMapper.toResponseList(reservationRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponseDto> findByClientId(Long idClient) {
        if (!clientRepository.existsById(idClient)) {
            throw new ResourceNotFoundException("Client", idClient);
        }
        return reservationMapper.toResponseList(reservationRepository.findByClientIdClient(idClient));
    }
}