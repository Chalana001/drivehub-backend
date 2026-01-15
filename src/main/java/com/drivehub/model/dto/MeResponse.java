package com.drivehub.model.dto;

import com.drivehub.entity.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MeResponse {
    private Long userId;
    private String fullName;
    private String email;
    private String phone;
    private Role role;
}
