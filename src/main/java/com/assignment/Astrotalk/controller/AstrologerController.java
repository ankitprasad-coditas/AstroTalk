package com.assignment.Astrotalk.controller;

import com.assignment.Astrotalk.dto.ApiResponseDto;
import com.assignment.Astrotalk.dto.AstrologerDto;
import com.assignment.Astrotalk.service.AstrologerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/astrologer/api/v1")
public class AstrologerController {

    private final AstrologerService astrologerService;

    public AstrologerController(AstrologerService astrologerService) {
        this.astrologerService = astrologerService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponseDto<AstrologerDto>> registerAstrologer(@RequestBody AstrologerDto astrologerDto) {

        AstrologerDto createdAstrologer = astrologerService.createNewAstrologer(astrologerDto);
        ApiResponseDto<AstrologerDto> response = new ApiResponseDto<>(createdAstrologer, HttpStatus.CREATED.value(), "Astrologer registered successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
