package com.assignment.Astrotalk.service;

import com.assignment.Astrotalk.dto.ConsultationDto;
import com.assignment.Astrotalk.entity.Client;
import com.assignment.Astrotalk.entity.Consultation;
import com.assignment.Astrotalk.exception.ClientNotFoundException;
import com.assignment.Astrotalk.exception.NotAllowedException;
import com.assignment.Astrotalk.repository.ClientRepo;
import com.assignment.Astrotalk.repository.ConsultationRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConsultationService {

    private ConsultationRepo consultationRepo;
    private ClientRepo clientRepo;
    private ObjectMapper objectMapper;


    @Autowired
    public ConsultationService(ConsultationRepo consultationRepo, ClientRepo clientRepo, ObjectMapper objectMapper) {
        this.consultationRepo = consultationRepo;
        this.clientRepo = clientRepo;
        this.objectMapper = objectMapper;
    }

    public List<ConsultationDto> getAllConsultation(int pageNo) {
        Pageable page = Pageable.ofSize(pageNo);
        List<Consultation> list = consultationRepo.findAll(page).stream().toList();
        return list.stream()
                .map(consults -> {
                    ConsultationDto consultationDto = objectMapper.convertValue(consults, ConsultationDto.class);
                    return consultationDto;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public ConsultationDto createConsultations(ConsultationDto consultationDto) {
        Optional<Client> optionalClient = clientRepo.findById(consultationDto.getClientId());
        Consultation consultations = new Consultation();
        if (optionalClient.isPresent()) {
                Consultation newconsultation = new Consultation();
                if(consultationDto.getConsultationDate().isBefore(LocalDate.now())){
                    throw new NotAllowedException("Cannot create backdated consultations");
                }
                newconsultation.setConsultationDate(consultationDto.getConsultationDate());
                newconsultation.setNextConsultationDate(consultationDto.getNextConsultationDate());
                newconsultation.setNotes(consultationDto.getNotes());
                newconsultation.setPrice(consultationDto.getPrice());
                newconsultation.setAmountPaid(consultationDto.getAmountPaid());
                newconsultation.setBalanceAmount(consultationDto.getBalanceAmount());
                newconsultation.setClient(optionalClient.get());
                if (optionalClient.get().getConsultation().isEmpty()) {
                    optionalClient.get().setConsultation(new ArrayList<>());
                    optionalClient.get().getConsultation().add(newconsultation);
                } else {
                    optionalClient.get().getConsultation().add(newconsultation);
                }
                clientRepo.save(optionalClient.get());
                consultations = newconsultation;
        } else {
            throw new ClientNotFoundException("Client Not Found");
        }
        ConsultationDto newConsultation = objectMapper.convertValue(consultations,ConsultationDto.class);
        newConsultation.setClientId(optionalClient.get().getId());
        return newConsultation;
    }

    public void deleteConsultation(Long id) {
        consultationRepo.deleteById(id);
    }

    public List<ConsultationDto> getUpcomingConsultations(LocalDate startDate, LocalDate endDate) {
//        System.out.println(startDate+ " "+endDate);
        List<Consultation> upcomingConsultationList = consultationRepo.findConsultationsWithinDateRange(startDate, endDate);
        return upcomingConsultationList.stream().map(
                consultation -> {
                    ConsultationDto consultationDto = objectMapper.convertValue(consultation,ConsultationDto.class);
                    consultationDto.setClientId(consultation.getClient().getId());
                    return consultationDto;
                }).collect(Collectors.toList());
    }


    /*public Consultation updateConsultations(ConsultationDto consultationDto) {

    }*/
}
