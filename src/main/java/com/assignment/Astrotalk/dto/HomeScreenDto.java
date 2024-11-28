package com.assignment.Astrotalk.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class HomeScreenDto {

    private JwtResponseDto jwtResponseDto;

    private List<ClientDto> clientDtoList   ;

    private List<ConsultationDto> consultationDtoList;

    private Double monthlyEarning;

}
