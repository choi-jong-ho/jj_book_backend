package com.example.jj_book.controller;

import com.example.jj_book.dto.MemberRequestDto;
import com.example.jj_book.dto.MemberResponseDto;
import com.example.jj_book.dto.TokenDto;
import com.example.jj_book.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto requestDto) {
        return ResponseEntity.ok(authService.signup(requestDto));
    }

    @GetMapping("/up")
    public String test() {
        System.out.println("up in");
        return "123";
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody MemberRequestDto requestDto) {
        return ResponseEntity.ok(authService.login(requestDto));
    }
}