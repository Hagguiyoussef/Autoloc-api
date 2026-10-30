package tn.esprit.autoloc.web.controller;

import java.time.LocalDateTime;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tn.esprit.autoloc.exception.DuplicateResourceException;
import tn.esprit.autoloc.exception.ResourceNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErreurApi> handleNotFound(ResourceNotFoundException ex) {
        return reponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErreurApi> handleBadRequest(IllegalArgumentException ex) {
        return reponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErreurApi> handleDuplicate(DuplicateResourceException ex) {
        return reponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErreurApi> handleIntegrity(DataIntegrityViolationException ex) {
        return reponse(HttpStatus.CONFLICT,
                "Operation refusee : contrainte d'integrite violee (valeur en double, champ obligatoire manquant ou ressource encore referencee)");
    }

    private ResponseEntity<ErreurApi> reponse(HttpStatus status, String message) {
        return ResponseEntity.status(status)
                .body(new ErreurApi(status.value(), message, LocalDateTime.now()));
    }

    public record ErreurApi(int statut, String message, LocalDateTime horodatage) {
    }
}