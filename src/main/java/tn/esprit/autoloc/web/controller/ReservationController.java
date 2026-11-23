package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloc.service.IReservationService;
import tn.esprit.autoloc.web.dto.ReservationResponseDto;

@Tag(name = "Réservations", description = "Consultation des réservations")
@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final IReservationService reservationService;

    @Operation(summary = "Lister les réservations")
    @ApiResponse(responseCode = "200", description = "Liste retournée")
    @GetMapping
    public List<ReservationResponseDto> findAll() {
        return reservationService.findAll();
    }

    @Operation(summary = "Lister les réservations d'un client")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste retournée"),
            @ApiResponse(responseCode = "404", description = "Client introuvable")
    })
    @GetMapping("/client/{idClient}")
    public List<ReservationResponseDto> findByClient(@Parameter(description = "Identifiant du client") @PathVariable Long idClient) {
        return reservationService.findByClientId(idClient);
    }
}