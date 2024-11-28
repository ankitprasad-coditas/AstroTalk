package com.assignment.Astrotalk.controller;

import com.assignment.Astrotalk.dto.ApiResponseDto;
import com.assignment.Astrotalk.dto.ClientDto;
import com.assignment.Astrotalk.entity.Client;
import com.assignment.Astrotalk.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/client/api/v1")
public class ClientController {

    private final ClientService clientService;
    private final ObjectMapper objectMapper;

    @Autowired
    public ClientController(ClientService clientService, ObjectMapper objectMapper) {
        this.clientService = clientService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/newClient")
    public ResponseEntity<ApiResponseDto<ClientDto>> newClient(@RequestParam("data") String clientData, @RequestParam("image") MultipartFile file) throws IOException {
        ClientDto clientDto = objectMapper.readValue(clientData, ClientDto.class);
        byte[] bytes = file.getBytes();
        clientDto.setChart(bytes);
        Client newClient = clientService.createNewClient(clientDto);
        ClientDto createdClient = objectMapper.convertValue(newClient, ClientDto.class);
        ApiResponseDto<ClientDto> response = new ApiResponseDto<>(createdClient, HttpStatus.CREATED.value(), "Client registered successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/allClients")
    public ResponseEntity<ApiResponseDto<List<ClientDto>>> allClients() {
        List<ClientDto> allClients = clientService.allClients();
        ApiResponseDto<List<ClientDto>> response = new ApiResponseDto<>(allClients, HttpStatus.CREATED.value(), "All Clients");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/clientById/{id}")
    public ResponseEntity<ApiResponseDto<ClientDto>> clientById(@PathVariable Long id) {
        ClientDto theCline = objectMapper.convertValue(clientService.getClientById(id), ClientDto.class);
        ApiResponseDto<ClientDto> response = new ApiResponseDto<>(theCline,HttpStatus.OK.value(),"Successfully Fetched Client Data");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/deleteClient/{id}")
    public ResponseEntity<ApiResponseDto<String>> removeClient(@PathVariable  Long id) {
        clientService.deleteClient(id);
        ApiResponseDto<String> response = new ApiResponseDto<>("User Not Found",HttpStatus.NOT_FOUND.value(),"Client Deleted Successfully");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}

