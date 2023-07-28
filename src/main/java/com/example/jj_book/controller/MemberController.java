package com.example.jj_book.controller;


import com.example.jj_book.dto.ChangePasswordRequestDto;
import com.example.jj_book.dto.MemberRequestDto;
import com.example.jj_book.dto.MemberResponseDto;
import com.example.jj_book.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/me")
    public ResponseEntity<MemberResponseDto> getMyMemberInfo() {
        MemberResponseDto myInfoBySecurity = memberService.getMyInfoBySecurity();
        System.out.println(myInfoBySecurity.getEmail());
        System.out.println(myInfoBySecurity.getUserName());
        return ResponseEntity.ok((myInfoBySecurity));
        // return ResponseEntity.ok(memberService.getMyInfoBySecurity());
    }

    @PostMapping("/username")
    public ResponseEntity<MemberResponseDto> setMemberUserName(@RequestBody MemberRequestDto request) {
        return ResponseEntity.ok(memberService.changeMemberUserName(request.getEmail(), request.getUserName()));
    }

    @PostMapping("/password")
    public ResponseEntity<MemberResponseDto> setMemberPassword(@RequestBody ChangePasswordRequestDto request) {
        return ResponseEntity.ok(memberService.changeMemberPassword(request.getExPassword(), request.getNewPassword()));
    }
}
