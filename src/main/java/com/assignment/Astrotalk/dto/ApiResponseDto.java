package com.assignment.Astrotalk.dto;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ApiResponseDto<T> {

    private T data;
    private int status;
    private String message;
    private Instant timeStamp;

    public ApiResponseDto() {
    }

    public ApiResponseDto(T data, int status, String message) {
        this.data = data;
        this.status = status;
        this.message = message;
        this.timeStamp = Instant.now();
    }

    public ApiResponseDto(int status, String message) {
        this.status = status;
        this.message = message;
        this.timeStamp = Instant.now();
    }
}
