package com.assignment.Astrotalk.controller;

import com.assignment.Astrotalk.dto.ApiResponseDto;
import com.assignment.Astrotalk.dto.ClientDto;
import com.assignment.Astrotalk.entity.Client;
import com.assignment.Astrotalk.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/client/api/v1")
public class ClientController {

    private final ClientService clientService;
    private final ObjectMapper objectMapper;
    private final Validator validator;

    @Autowired
    public ClientController(ClientService clientService, ObjectMapper objectMapper, Validator validator) {
        this.clientService = clientService;
        this.objectMapper = objectMapper;
        this.validator = validator;
    }

    @Operation(summary = "Create New Client ", description = "Returns The Clients Created")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully Created", content = @Content),
            @ApiResponse(responseCode = "401", description = "Authentication Failed", content = @Content),
            @ApiResponse(responseCode = "403", description = "Not Authorised", content = @Content),
            @ApiResponse(responseCode = "404", description = "Missing Resource", content = @Content)
    })
    @PostMapping("/newClient")
    public ResponseEntity<ApiResponseDto<ClientDto>> newClient(@RequestParam("data") String clientData, @RequestParam("image") MultipartFile file) throws IOException {
        ClientDto clientDto = objectMapper.readValue(clientData, ClientDto.class);
        /*if(!validator.validate(clientDto).isEmpty()) {
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }else{*/
            byte[] bytes = file.getBytes();
            clientDto.setChart(bytes);
            ClientDto createdClient = clientService.createNewClient(clientDto);
            ApiResponseDto<ClientDto> response = new ApiResponseDto<>(createdClient, HttpStatus.CREATED.value(), "Client registered successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
//        }

    }

    @Operation(summary = "View All Client ", description = "Returns All The Clients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully Fetched", content = @Content),
            @ApiResponse(responseCode = "401", description = "Authentication Failed", content = @Content),
            @ApiResponse(responseCode = "403", description = "Not Authorised", content = @Content),
            @ApiResponse(responseCode = "404", description = "Missing Resource", content = @Content)
    })
    @GetMapping("/allClients")
    public ResponseEntity<ApiResponseDto<List<ClientDto>>> allClients(@RequestParam(value = "sorting", required = false) boolean isSort) {
        List<ClientDto> allClients = clientService.allClients();
        if (isSort) {
            Collections.sort(allClients, new Comparator<ClientDto>() {
                @Override
                public int compare(ClientDto o1, ClientDto o2) {
                    return o1.getName().compareTo(o2.toString());
                }
            });
        }
        ApiResponseDto<List<ClientDto>> response = new ApiResponseDto<>(allClients, HttpStatus.OK.value(), "All Clients");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "View Client By Id", description = "Returns The Client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully Fetched", content = @Content),
            @ApiResponse(responseCode = "401", description = "Authentication Failed", content = @Content),
            @ApiResponse(responseCode = "403", description = "Not Authorised", content = @Content),
            @ApiResponse(responseCode = "404", description = "Missing Resource", content = @Content)
    })
    @GetMapping("/clientById/{id}")
    public ResponseEntity<ApiResponseDto<ClientDto>> clientById(@PathVariable Long id) {
        ClientDto theClient = objectMapper.convertValue(clientService.getClientById(id), ClientDto.class);
        theClient.setChart(Base64.getEncoder().encode(theClient.getChart()));
        ApiResponseDto<ClientDto> response = new ApiResponseDto<>(theClient, HttpStatus.OK.value(), "Successfully Fetched Client Data");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Search Client By Name", description = "Returns The Client(s)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully Fetched", content = @Content),
            @ApiResponse(responseCode = "401", description = "Authentication Failed", content = @Content),
            @ApiResponse(responseCode = "403", description = "Not Authorised", content = @Content),
            @ApiResponse(responseCode = "404", description = "Missing Resource", content = @Content)
    })
    @GetMapping("/searchByName/{name}")
    public ResponseEntity<ApiResponseDto<List<ClientDto>>> searchByName(@PathVariable String name) {
        List<ClientDto> theClients = clientService.searchByName(name);
        ApiResponseDto<List<ClientDto>> response = new ApiResponseDto<>(theClients, HttpStatus.OK.value(), "Clients Fetched Successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Delete Client by Ids", description = "Removes the Client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Deleted", content = @Content),
            @ApiResponse(responseCode = "401", description = "Authentication Failed", content = @Content),
            @ApiResponse(responseCode = "403", description = "Not Authorised", content = @Content),
            @ApiResponse(responseCode = "404", description = "Missing Resource", content = @Content)
    })
    @DeleteMapping("/deleteClient/{id}")
    public ResponseEntity<ApiResponseDto<String>> removeClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        ApiResponseDto<String> response = new ApiResponseDto<>("User Not Found", HttpStatus.NOT_FOUND.value(), "Client Deleted Successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Update Client Details", description = "Returns the Updates Client Data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Deleted", content = @Content),
            @ApiResponse(responseCode = "401", description = "Authentication Failed", content = @Content),
            @ApiResponse(responseCode = "403", description = "Not Authorised", content = @Content),
            @ApiResponse(responseCode = "404", description = "Missing Resource", content = @Content)
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponseDto<ClientDto>> updateClient(@PathVariable Long id, @RequestBody ClientDto clientDto){
        ApiResponseDto response = new ApiResponseDto(clientService.updateClient(id,clientDto),HttpStatus.OK.value(),"Client Details Updated");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getClientImage(@PathVariable Long id) {
        Client clientOptional = clientService.getClientById(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + clientOptional.getName() + "-image.png\"")
                .contentType(MediaType.IMAGE_JPEG)
                .body(clientOptional.getChart());

    }
}

