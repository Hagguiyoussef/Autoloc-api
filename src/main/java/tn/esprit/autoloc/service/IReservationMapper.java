package tn.esprit.autoloc.service;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.web.dto.ReservationResponseDto;

@Mapper(componentModel = "spring")
public interface IReservationMapper {

    @Mapping(source = "client.nom", target = "nomClient")
    @Mapping(source = "client.prenom", target = "prenomClient")
    @Mapping(source = "vehicule.marque", target = "marque")
    @Mapping(source = "vehicule.modele", target = "modele")
    @Mapping(source = "vehicule.immatriculation", target = "immatriculation")
    ReservationResponseDto toResponse(Reservation reservation);

    List<ReservationResponseDto> toResponseList(List<Reservation> reservations);
}