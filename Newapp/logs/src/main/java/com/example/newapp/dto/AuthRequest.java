package com.example.newapp.dto;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {
    private String username;
    private String password;
}
