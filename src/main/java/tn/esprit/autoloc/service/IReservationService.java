package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.web.dto.ReservationResponseDto;

public interface IReservationService {

    List<ReservationResponseDto> findAll();

    List<ReservationResponseDto> findByClientId(Long idClient);
}