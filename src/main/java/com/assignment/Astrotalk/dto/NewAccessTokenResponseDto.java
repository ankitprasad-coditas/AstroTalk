package com.assignment.Astrotalk.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewAccessTokenResponseDto {

    @NotBlank(message = "Pass Refresh Token")
    String accessToken;
}
