package com.assignment.Astrotalk.controller;

import com.assignment.Astrotalk.dto.ApiResponseDto;
import com.assignment.Astrotalk.dto.ConsultationDto;
import com.assignment.Astrotalk.service.ConsultationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/consultation/api/v1")
public class ConsultationController {

    private ConsultationService consultationService;
    private ObjectMapper objectMapper;

    @Autowired
    public ConsultationController(ConsultationService consultationService, ObjectMapper objectMapper) {
        this.consultationService = consultationService;
        this.objectMapper = objectMapper;
    }

    @Operation(summary = "View All Consultations", description = "Returns All the Consultations of the Astrologer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Fetched",content = @Content),
            @ApiResponse(responseCode = "401", description = "Authentication Failed",content = @Content),
            @ApiResponse(responseCode = "403", description = "Not Authorised",content = @Content),
            @ApiResponse(responseCode = "404", description = "Missing Resource",content = @Content)
    })
    @GetMapping("/allConsultations/pageNo")
    public ResponseEntity<ApiResponseDto<List<ConsultationDto>>> getAllConsults(@RequestParam("pageNo") int pageNo) {
        List<ConsultationDto> allConsultation = consultationService.getAllConsultation(pageNo);
        ApiResponseDto<List<ConsultationDto>> response = new ApiResponseDto<>(allConsultation, HttpStatus.OK.value(), "All Consultations");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @Operation(summary = "View All Upcoming Consultations", description = "Returns All the Upcoming Consultations of the Astrologer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Fetched",content = @Content),
            @ApiResponse(responseCode = "401", description = "Authentication Failed",content = @Content),
            @ApiResponse(responseCode = "403", description = "Not Authorised",content = @Content),
            @ApiResponse(responseCode = "404", description = "Missing Resource",content = @Content)
    })
    @GetMapping("/getUpcomingConsultations")
    public ResponseEntity<ApiResponseDto<List<ConsultationDto>>> getUpcomingConsultation(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate){
        List<ConsultationDto> consultationDtoList = consultationService.getUpcomingConsultations(Date.valueOf(startDate).toLocalDate(),Date.valueOf(endDate).toLocalDate());
        ApiResponseDto<List<ConsultationDto>> response = new ApiResponseDto<>(consultationDtoList, HttpStatus.OK.value(), "All Upcoming Consultations");
        return new ResponseEntity<>(response,HttpStatus.OK);

    }

    @Operation(summary = "Create New Consultation", description = "Returns the Newly Created Consultation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully Created",content = @Content),
            @ApiResponse(responseCode = "401", description = "Authentication Failed",content = @Content),
            @ApiResponse(responseCode = "403", description = "Not Authorised",content = @Content),
            @ApiResponse(responseCode = "404", description = "Missing Resource",content = @Content)
    })
    @PostMapping("/newConsultation")
    public ResponseEntity<ApiResponseDto<ConsultationDto>> newConsultation(@RequestBody @Valid ConsultationDto consultationDto){
        ConsultationDto newConsult = consultationService.createConsultations(consultationDto);
        if(newConsult!=null){
            ApiResponseDto<ConsultationDto> response = new ApiResponseDto<>(newConsult,HttpStatus.CREATED.value(),"Consultation Data Saved");
            return new ResponseEntity<>(response,HttpStatus.CREATED);
        }
        else{
            throw new NullPointerException("Data Not Saved");
        }
    }

    @Operation(summary = "Delete A Consultation", description = "Deletes The consultation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Created",content = @Content),
            @ApiResponse(responseCode = "401", description = "Authentication Failed",content = @Content),
            @ApiResponse(responseCode = "403", description = "Not Authorised",content = @Content),
            @ApiResponse(responseCode = "404", description = "Missing Resource",content = @Content)
    })
    @DeleteMapping("/deleteConsultation/{id}")
    public ResponseEntity<ApiResponseDto<String>> deleteConsultation(@PathVariable  Long id) {
        consultationService.deleteConsultation(id);
        ApiResponseDto<String> response = new ApiResponseDto<>("User Not Found",HttpStatus.NOT_FOUND.value(),"Consultation Deleted Successfully");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    /*@PutMapping("/updateConsultation/{id}")
    public ResponseEntity<ApiResponseDto<ConsultationDto>> updateConsultation(@RequestBody ConsultationDto consultationDto){
        Consultation newConsult = consultationService.updateConsultations(consultationDto);
        ApiResponseDto<ConsultationDto> response = new ApiResponseDto<>((objectMapper.convertValue(newConsult,ConsultationDto.class)),HttpStatus.CREATED.value(),"Consultation Data Saved");
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }*/
}
