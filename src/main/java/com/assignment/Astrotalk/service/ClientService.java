package com.assignment.Astrotalk.service;

import com.assignment.Astrotalk.dto.ClientDto;
import com.assignment.Astrotalk.entity.Client;
import com.assignment.Astrotalk.exception.ClientNotFoundException;
import com.assignment.Astrotalk.exception.NotAllowedException;
import com.assignment.Astrotalk.repository.ClientRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private final ClientRepo clientRepo;
    private final ObjectMapper objectMapper;

    @Autowired
    public ClientService(ClientRepo clientRepo, ObjectMapper objectMapper) {
        this.clientRepo = clientRepo;
        this.objectMapper = objectMapper;
    }

    // Creating New Client
    public ClientDto createNewClient(ClientDto clientDto) {
        if (clientRepo.existsByNameAndDobAndPlaceOfBirthAndTimeOfBirth(clientDto.getName(), clientDto.getDob(), clientDto.getPlaceOfBirth(), clientDto.getTimeOfBirth())) {
            throw new NotAllowedException("Client Already Exists");
        }
        Client newClient = clientRepo.save(objectMapper.convertValue(clientDto, Client.class));
        return objectMapper.convertValue(newClient,ClientDto.class);
    }

    //Getting ALl Clients
    public List<ClientDto> allClients() {
        return clientRepo.findAll().stream()
                .map(client -> {
                    ClientDto clientDto = objectMapper.convertValue(client, ClientDto.class);
                    clientDto.setChart(Base64.getEncoder().encode(client.getChart()));
                    return clientDto;
                })
                .collect(Collectors.toList());
    }

    // Getting Client By ID
    public Client getClientById(Long id) {
        return clientRepo.findById(id).orElseThrow(() -> new ClientNotFoundException("Client Doesn't Exists"));

    }

    //Updating a Client


    // Deleting Client
    public void deleteClient(Long id) {
        clientRepo.deleteById(id);
    }

}
