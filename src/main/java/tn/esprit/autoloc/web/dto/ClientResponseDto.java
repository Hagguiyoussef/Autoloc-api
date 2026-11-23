package tn.esprit.autoloc.web.dto;

public record ClientResponseDto(
        Long idClient,
        String nom,
        String prenom,
        String telephone
) {
}