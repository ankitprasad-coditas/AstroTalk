package com.assignment.Astrotalk.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConsultationDto {
    private Long id;

    @NotNull(message = "Need A Consultation Date")
    private LocalDate consultationDate;

    private LocalDate nextConsultationDate;

    @NotEmpty(message = "Add Consultation Notes")
    private String notes;

    @Positive(message = "Add Consultation Price")
    private double price;

    @Positive
    private double amountPaid = 0d;

    @Positive
    private double balanceAmount = 0d;

    private Long clientId;
}
