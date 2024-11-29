package com.assignment.Astrotalk.dto;


import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AstrologerDto {

    private Long id;

    private String username;

    @Email(message = "Enter a Valid Email Id")
    private String emailId;

    private String password;
}
