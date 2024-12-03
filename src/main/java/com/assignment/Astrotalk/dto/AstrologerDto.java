package com.assignment.Astrotalk.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AstrologerDto {

    private Long id;

    @NotBlank(message = "Please Enter A Username")
    private String username;

    @Email(message = "Enter a Valid Email Id")
    private String emailId;

    private String password;
}
