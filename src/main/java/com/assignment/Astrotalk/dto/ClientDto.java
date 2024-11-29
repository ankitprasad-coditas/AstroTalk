package com.assignment.Astrotalk.dto;

import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Enter A Valid Date Of Birth")
    private LocalDate dob;

    @NotBlank(message = "Enter A Valid Time Of Birth")
    private String timeOfBirth;

    @NotBlank(message = "Enter A Valid Place Of Birth")
    private String placeOfBirth;

    private byte[] chart;

    private Double balanceAmount;
}
