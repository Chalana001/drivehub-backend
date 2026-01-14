package com.drivehub.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String role;
}
