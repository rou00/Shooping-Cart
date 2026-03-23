package com.rou00.shopcart.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class LoginRequest {

    @NotBlank // to make sure the user doesn't submit empty fields
    private String email;

    @NotBlank
    private String password;
}
