package com.assignment.Astrotalk.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ConsultationDto {
    private Long id;

    @NotBlank(message = "Need A Consultation Date")
    private LocalDate consultationDate;

    private LocalDate nextConsultationDate;

    private String notes;

    private double price;

    private double amountPaid;

    private double balanceAmount = 0d;

    private Long clientId;

    public ConsultationDto(Long id, LocalDate consultationDate, LocalDate nextConsultationDate, String notes, double price, double amountPaid, Double balanceAmount, Long clientId) {
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

    public LocalDate getConsultationDate() {
        return consultationDate;
    }

    public void setConsultationDate(LocalDate consultationDate) {
        this.consultationDate = consultationDate;
    }

    public LocalDate getNextConsultationDate() {
        return nextConsultationDate;
    }

    public void setNextConsultationDate(LocalDate nextConsultationDate) {
        this.nextConsultationDate = nextConsultationDate;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Double getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(Double balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
