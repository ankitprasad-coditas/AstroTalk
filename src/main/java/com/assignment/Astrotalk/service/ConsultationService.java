package com.assignment.Astrotalk.service;

import com.assignment.Astrotalk.dto.ConsultationDto;
import com.assignment.Astrotalk.entity.Client;
import com.assignment.Astrotalk.entity.Consultation;
import com.assignment.Astrotalk.exception.ClientNotFoundException;
import com.assignment.Astrotalk.repository.ClientRepo;
import com.assignment.Astrotalk.repository.ConsultationRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<ConsultationDto> getAllConsultation() {
        return consultationRepo.findAll().stream()
                .map(consults -> {
                    ConsultationDto consultationDto = objectMapper.convertValue(consults, ConsultationDto.class);
                    return consultationDto;
                })
                .collect(Collectors.toList());
    }

    public Consultation createConsultations(ConsultationDto consultationDto) {
        Optional<Client> optionalClient= clientRepo.findById(consultationDto.getClientId());
        if (optionalClient.isPresent()) {
            try {
                Client existingClient = optionalClient.get();
                Consultation consultation = objectMapper.convertValue(consultationDto, Consultation.class);
                consultation.setClient(existingClient);
                existingClient.getConsultation().add(consultation);
               Client client =  clientRepo.save(existingClient);
                return consultation;

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            throw new ClientNotFoundException("Client Not Found");
        }
        return null;
    }

    public void deleteConsultation(Long id) {
        consultationRepo.deleteById(id);
    }

    /*public Consultation updateConsultations(ConsultationDto consultationDto) {

    }*/
}
