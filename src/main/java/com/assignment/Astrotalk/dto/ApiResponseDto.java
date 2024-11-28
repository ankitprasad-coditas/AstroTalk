package com.assignment.Astrotalk.dto;

import lombok.Data;

import java.time.Instant;

@Data
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
}
