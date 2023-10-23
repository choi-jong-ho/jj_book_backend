package com.example.jj_book.controller;

import com.example.jj_book.dto.MemberFormDto;
import com.example.jj_book.entity.Member;
import com.example.jj_book.jwt.JwtTokenProvider;
import com.example.jj_book.service.KakaoService;
import com.example.jj_book.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
public class KakaoController {

    private final PasswordEncoder passwordEncoder;
    private final KakaoService kakaoService;
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/login/kakao")
    public String kakaoCallBack(@RequestParam(value = "code", required = false) String code) throws Exception{
        System.out.println("#########" + code);

        String access_Token = kakaoService.getAccessToken(code);
        System.out.println("###access_Token#### : " + access_Token);

        HashMap<String, Object> userInfo = kakaoService.getUserInfo(access_Token);
        System.out.println("###access_Token#### : " + access_Token);
        System.out.println("###userInfo#### : " + userInfo.get("email"));
        System.out.println("###nickname#### : " + userInfo.get("nickname"));

        String email = String.valueOf(userInfo.get("email"));
        String name = String.valueOf(userInfo.get("nickname"));

        Member member = memberService.getMember(email);
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail(email);
        memberFormDto.setName(name);

        if(member == null){
            Member newMember = Member.builder()
                    .name(memberFormDto.getName())
                    .phone(memberFormDto.getPhone())
                    .email(memberFormDto.getEmail())
                    .password(passwordEncoder.encode(memberFormDto.getPassword()))
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build();
            memberService.saveMember(newMember, memberFormDto);

            member = newMember;
        }

        return jwtTokenProvider.createToken(member.getEmail(), member.getRoles());
    }
}
