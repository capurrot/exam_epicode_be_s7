package it.epicode.exam_epicode_be_s7.auth;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
