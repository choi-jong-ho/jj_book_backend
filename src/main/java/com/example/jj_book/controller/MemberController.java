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
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    @Autowired
    PasswordEncoder passwordEncoder;

    private final MemberService memberService;

    @PostMapping(value = "/signup")
    public ResponseEntity singUp(@RequestBody @Valid MemberFormDto memberFormDto, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        }

        Member member = Member.createMember(memberFormDto, passwordEncoder);
        memberService.saveMember(member, memberFormDto);

        return ResponseEntity.ok(memberFormDto);
    }

    @GetMapping(value = "/login/error")
    public String error(@RequestParam(value = "error")String error, @RequestParam(value = "exception") String exception){

        String error1 = error;
        String exception1 = exception;

        System.out.println("error : "+ error1);
        System.out.println("exception : "+ exception1);

        return exception1;
    }

    @PostMapping(value = "/delete")
    public ResponseEntity memberDelete(@RequestBody MemberFormDto memberFormDto, Principal principal){

        try {
            memberFormDto.setEmail(principal.getName());
            memberService.deleteMemeber(memberFormDto);
        } catch (Exception e){
        }

        return ResponseEntity.ok(memberFormDto);
    }

    @PostMapping(value = "/update")
    public ResponseEntity memberUpdate(@RequestBody MemberFormDto memberFormDto, Principal principal){

        memberFormDto.setEmail(principal.getName());
        try {
        memberService.updateMember(memberFormDto, passwordEncoder);
        } catch (Exception e) {
        }

        return ResponseEntity.ok(memberFormDto);
    }

    @GetMapping("/info")
    public ResponseEntity getUserDetails(Principal principal) {
        try {
            Member member = memberService.getMember(principal.getName());
            return ResponseEntity.ok(member);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
