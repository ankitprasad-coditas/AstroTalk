package com.assignment.Astrotalk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {
    private Long id;
    private String name;
    private LocalDate dob;
    private String timeOfBirth;
    private String placeOfBirth;
    private byte[] chart;
    private Double balanceAmount;
}
