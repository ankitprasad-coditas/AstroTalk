package com.assignment.Astrotalk.controller;

import com.assignment.Astrotalk.dto.ApiResponseDto;
import com.assignment.Astrotalk.dto.ConsultationDto;
import com.assignment.Astrotalk.entity.Consultation;
import com.assignment.Astrotalk.exception.NotAllowedException;
import com.assignment.Astrotalk.service.ConsultationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/allConsultations")
    public ResponseEntity<ApiResponseDto<List<ConsultationDto>>> getAllConsults() {
        List<ConsultationDto> allConsultation = consultationService.getAllConsultation();
        ApiResponseDto<List<ConsultationDto>> response = new ApiResponseDto<>(allConsultation, HttpStatus.OK.value(), "All Consultations");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/newConsultation")
    public ResponseEntity<ApiResponseDto<ConsultationDto>> newConsultation(@RequestBody ConsultationDto consultationDto){
        Consultation newConsult = consultationService.createConsultations(consultationDto);
        if(newConsult!=null){
            ApiResponseDto<ConsultationDto> response = new ApiResponseDto<>((objectMapper.convertValue(newConsult,ConsultationDto.class)),HttpStatus.CREATED.value(),"Consultation Data Saved");
            return new ResponseEntity<>(response,HttpStatus.CREATED);
        }
        else{
            throw new NullPointerException("Data Not Saved");
        }
    }

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
