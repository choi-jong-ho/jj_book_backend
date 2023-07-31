package com.example.jj_book.controller;


import com.example.jj_book.dto.AddressRequestDto;
import com.example.jj_book.dto.ChangePasswordRequestDto;
import com.example.jj_book.dto.MemberRequestDto;
import com.example.jj_book.dto.MemberResponseDto;
import com.example.jj_book.service.AddressService;
import com.example.jj_book.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final AddressService addressService;

    @GetMapping("/me")
    public ResponseEntity<MemberResponseDto> getMyMemberInfo() {
        MemberResponseDto myInfoBySecurity = memberService.getMyInfoBySecurity();

        System.out.println("getUserName : "+myInfoBySecurity.getUserName());
        System.out.println("getEmail : "+myInfoBySecurity.getEmail());
        System.out.println("getUserName : "+myInfoBySecurity.getPhone());
        System.out.println("getUserName : "+myInfoBySecurity.getGrade());

        return ResponseEntity.ok((myInfoBySecurity));
    }

    @PostMapping("/username")
    public ResponseEntity<MemberResponseDto> setMemberUserName(@RequestBody MemberRequestDto request) {
        return ResponseEntity.ok(memberService.changeMemberUserName(request.getEmail(), request.getUserName()));
    }

    @PostMapping("/password")
    public ResponseEntity<MemberResponseDto> setMemberPassword(@RequestBody ChangePasswordRequestDto request) {
        return ResponseEntity.ok(memberService.changeMemberPassword(request.getExPassword(), request.getNewPassword()));
    }

    @PostMapping("/addressreg")
    public ResponseEntity<?> addressreg(@RequestBody AddressRequestDto addressRequestDto) {

        return ResponseEntity.ok((addressService.save(addressRequestDto)));
    }
}
