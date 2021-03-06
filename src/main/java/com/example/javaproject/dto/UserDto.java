package com.example.javaproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {

    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String fullName;

    @NotBlank(message = "Email is mandatory")
    @Email
    private String email;

    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "Password is mandatory")
    private String password;

    @NotBlank(message = "Address is mandatory")
    private String address;

    @NotBlank(message = "Phone Number is mandatory")
    private String phoneNumber;

    private String otherInformation;
}
