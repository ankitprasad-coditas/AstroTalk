package com.assignment.Astrotalk.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

@Entity
@Data
@DynamicUpdate
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "consultations")
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate consultationDate;

    private LocalDate nextConsultationDate;

    private String notes;

    private double price;

    private double amountPaid;

    private Double balanceAmount  = 0d;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clients_id")
    private Client client;

    public Consultation() {
    }

    public Consultation(Long id, LocalDate consultationDate, LocalDate nextConsultationDate, String notes, double price, double amountPaid, Double balanceAmount, Client client) {
        this.id = id;
        this.consultationDate = consultationDate;
        this.nextConsultationDate = nextConsultationDate;
        this.notes = notes;
        this.price = price;
        this.amountPaid = amountPaid;
        this.balanceAmount = balanceAmount;
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
