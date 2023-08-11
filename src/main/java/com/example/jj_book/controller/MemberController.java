package com.example.jj_book.controller;

import com.example.jj_book.dto.MemberFormDto;
import com.example.jj_book.entity.Member;
import com.example.jj_book.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping(value = "/signup")
    public ResponseEntity singUp(@RequestBody @Valid MemberFormDto memberFormDto, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        }

        Member member = Member.createMember(memberFormDto, passwordEncoder);
        memberService.saveMember(member);

        return ResponseEntity.ok(member);
    }

    @GetMapping(value = "/login/error")
    public String error(@RequestParam(value = "error")String error, @RequestParam(value = "exception") String exception){

        String error1 = error;
        String exception1 = exception;

        System.out.println("error : "+ error1);
        System.out.println("exception : "+ exception1);

        return exception1;
    }
}
