package com.example.jj_book.controller;

import com.example.jj_book.dto.MemberFormDto;
import com.example.jj_book.entity.Member;
import com.example.jj_book.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping(value = "/signup")
    public ResponseEntity singUp(@RequestBody @Valid MemberFormDto memberFormDto, BindingResult bindingResult){

        System.out.println("MemberFormDto" + memberFormDto.getEmail());

        if (bindingResult.hasErrors()){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        }

        Member member = Member.createMember(memberFormDto, passwordEncoder);
        memberService.saveMember(member);

        return ResponseEntity.ok(member);
    }
}
