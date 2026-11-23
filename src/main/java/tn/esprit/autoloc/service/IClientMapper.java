package tn.esprit.autoloc.service;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.web.dto.ClientRequestDto;
import tn.esprit.autoloc.web.dto.ClientResponseDto;

@Mapper(componentModel = "spring")
public interface IClientMapper {

    @Mapping(target = "idClient", ignore = true)
    @Mapping(target = "numPermis", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    @Mapping(target = "dateInscription", expression = "java(java.time.LocalDate.now())")
    Client toEntity(ClientRequestDto dto);

    ClientResponseDto toResponse(Client client);
}