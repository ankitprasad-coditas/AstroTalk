package com.assignment.Astrotalk.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ConsultationDto {
    private Long id;

    @NotBlank(message = "Need A Consultation Date")
    private LocalDate consultationDate;

    private LocalDate nextConsultationDate;

    @NotEmpty(message = "Add Consultation Notes")
    private String notes;

    @NotBlank(message = "Add Consultation Price")
    private double price;

    @Positive
    private double amountPaid = 0d;

    @Positive
    private double balanceAmount = 0d;

    private Long clientId;

    public ConsultationDto(Long id, LocalDate consultationDate, LocalDate nextConsultationDate, String notes, double price, double amountPaid, double balanceAmount, Long clientId) {
        this.id = id;
        this.consultationDate = consultationDate;
        this.nextConsultationDate = nextConsultationDate;
        this.notes = notes;
        this.price = price;
        this.amountPaid = amountPaid;
        this.balanceAmount = balanceAmount;
        this.clientId = clientId;
    }

    public ConsultationDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Need A Consultation Date") LocalDate getConsultationDate() {
        return consultationDate;
    }

    public void setConsultationDate(@NotBlank(message = "Need A Consultation Date") LocalDate consultationDate) {
        this.consultationDate = consultationDate;
    }

    public LocalDate getNextConsultationDate() {
        return nextConsultationDate;
    }

    public void setNextConsultationDate(LocalDate nextConsultationDate) {
        this.nextConsultationDate = nextConsultationDate;
    }

    public @NotEmpty(message = "Add Consultation Notes") String getNotes() {
        return notes;
    }

    public void setNotes(@NotEmpty(message = "Add Consultation Notes") String notes) {
        this.notes = notes;
    }

    @NotBlank(message = "Add Consultation Price")
    public double getPrice() {
        return price;
    }

    public void setPrice(@NotBlank(message = "Add Consultation Price") double price) {
        this.price = price;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public double getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(double balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
