package com.assignment.Astrotalk.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {
    private Long id;

    @NotBlank(message = "Please Provide A Name")
    private String name;

    @NotNull(message = "Enter A Valid Date Of Birth")
    private LocalDate dob;

    @NotBlank(message = "Enter A Valid Time Of Birth")
    private String timeOfBirth;

    @NotBlank(message = "Enter A Valid Place Of Birth")
    private String placeOfBirth;

    private byte[] chart;

    @Positive(message = "Enter A Positive Number or Zero(0)")
    private double balanceAmount = 0d;
}
