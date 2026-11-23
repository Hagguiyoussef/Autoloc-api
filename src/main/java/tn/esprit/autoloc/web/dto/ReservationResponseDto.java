package tn.esprit.autoloc.web.dto;

import java.time.LocalDate;
import tn.esprit.autoloc.domain.StatutReservation;

public record ReservationResponseDto(
        Long idReservation,
        LocalDate dateDebut,
        LocalDate dateFin,
        StatutReservation statut,
        String nomClient,
        String prenomClient,
        String marque,
        String modele,
        String immatriculation
) {
}