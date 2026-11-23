package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.NotBlank;

public record ClientRequestDto(
        @NotBlank(message = "Le nom est obligatoire") String nom,
        @NotBlank(message = "Le prenom est obligatoire") String prenom,
        String email,
        String telephone
) {
}