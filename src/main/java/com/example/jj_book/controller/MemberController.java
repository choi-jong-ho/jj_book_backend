package com.example.jj_book.controller;

import com.example.jj_book.dto.MemberFormDto;
import com.example.jj_book.entity.Member;
import com.example.jj_book.jwt.JwtTokenProvider;
import com.example.jj_book.repo.MemberRepository;
import com.example.jj_book.service.MemberService;
import lombok.RequiredArgsConstructor;
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

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
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

    // 로그인
    @PostMapping("/login")
    public String login(@RequestBody MemberFormDto memberFormDto) {
        Member member = memberRepository.findByEmail(memberFormDto.getEmail());

        if(member == null){
            throw new IllegalArgumentException("가입되지 않은 아이디입니다.");
        }

        if (!passwordEncoder.matches(memberFormDto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        return jwtTokenProvider.createToken(member.getEmail(), member.getRoles());
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
    public ResponseEntity memberUpdate(@RequestBody MemberFormDto memberFormDto, Principal principal) {

        memberFormDto.setEmail(principal.getName());
        Member member = null;
        try {
            member = memberService.updateMember(memberFormDto);
        } catch (Exception e) {
        }

        return ResponseEntity.ok(member);
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
