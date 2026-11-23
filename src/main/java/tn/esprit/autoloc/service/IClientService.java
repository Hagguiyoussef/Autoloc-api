package tn.esprit.autoloc.service;

import tn.esprit.autoloc.web.dto.ClientRequestDto;
import tn.esprit.autoloc.web.dto.ClientResponseDto;

public interface IClientService {

    ClientResponseDto create(ClientRequestDto dto);

    ClientResponseDto findById(Long id);
}