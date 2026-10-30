package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.service.IEmployeService;
import tn.esprit.autoloc.service.IVehiculeService;

@Tag(name = "Affectations", description = "Affectation des employés, véhicules et équipements")
@RestController
@RequiredArgsConstructor
public class AffectationController {

    private final IEmployeService employeService;
    private final IVehiculeService vehiculeService;

    @Operation(summary = "Désaffecter un employé de son agence")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employé désaffecté"),
            @ApiResponse(responseCode = "400", description = "Employé sans agence"),
            @ApiResponse(responseCode = "404", description = "Employé introuvable")
    })
    @PutMapping("/employes/{idEmploye}/desaffecter")
    public Employe desaffecterEmploye(@Parameter(description = "Identifiant de l'employé") @PathVariable Long idEmploye) {
        return employeService.desaffecterEmploye(idEmploye);
    }

    @Operation(summary = "Affecter un employé à une agence")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employé affecté"),
            @ApiResponse(responseCode = "400", description = "Employé déjà affecté à une agence"),
            @ApiResponse(responseCode = "404", description = "Employé ou agence introuvable")
    })
    @PutMapping("/employes/{idEmploye}/agences/{idAgence}")
    public Employe affecterEmploye(@Parameter(description = "Identifiant de l'employé") @PathVariable Long idEmploye,
                                   @Parameter(description = "Identifiant de l'agence") @PathVariable Long idAgence) {
        return employeService.affecterEmploye(idEmploye, idAgence);
    }

    @Operation(summary = "Affecter un véhicule à une agence")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Véhicule affecté"),
            @ApiResponse(responseCode = "400", description = "Véhicule déjà affecté à cette agence"),
            @ApiResponse(responseCode = "404", description = "Véhicule ou agence introuvable")
    })
    @PutMapping("/vehicules/{idVehicule}/agences/{idAgence}")
    public Vehicule affecterVehicule(@Parameter(description = "Identifiant du véhicule") @PathVariable Long idVehicule,
                                     @Parameter(description = "Identifiant de l'agence") @PathVariable Long idAgence) {
        return vehiculeService.affecterVehicule(idVehicule, idAgence);
    }

    @Operation(summary = "Installer un équipement sur un véhicule")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Équipement installé"),
            @ApiResponse(responseCode = "400", description = "Équipement déjà installé"),
            @ApiResponse(responseCode = "404", description = "Véhicule ou équipement introuvable")
    })
    @PutMapping("/vehicules/{idVehicule}/equipements/{idEquipement}")
    public Vehicule affecterEquipement(@Parameter(description = "Identifiant du véhicule") @PathVariable Long idVehicule,
                                       @Parameter(description = "Identifiant de l'équipement") @PathVariable Long idEquipement) {
        return vehiculeService.affecterEquipement(idVehicule, idEquipement);
    }

    @Operation(summary = "Retirer un équipement d'un véhicule")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Équipement retiré"),
            @ApiResponse(responseCode = "400", description = "Équipement non installé"),
            @ApiResponse(responseCode = "404", description = "Véhicule ou équipement introuvable")
    })
    @DeleteMapping("/vehicules/{idVehicule}/equipements/{idEquipement}")
    public Vehicule desaffecterEquipement(@Parameter(description = "Identifiant du véhicule") @PathVariable Long idVehicule,
                                          @Parameter(description = "Identifiant de l'équipement") @PathVariable Long idEquipement) {
        return vehiculeService.desaffecterEquipement(idVehicule, idEquipement);
    }
}