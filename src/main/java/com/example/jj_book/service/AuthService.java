package com.example.jj_book.service;

import com.example.jj_book.dto.*;
import com.example.jj_book.jwt.TokenProvider;
import com.example.jj_book.repo.AddressRepository;
import com.example.jj_book.repo.Member;
import com.example.jj_book.repo.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final AuthenticationManagerBuilder managerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AddressRepository addressRepository;
    public MemberResponseDto signup(MemberRequestDto requestDto) {
        if (memberRepository.existsByEmail(requestDto.getEmail())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }

        Member member = requestDto.toMember(passwordEncoder);
        MemberResponseDto memberResponseDto = MemberResponseDto.of(memberRepository.save(member));

        AddressRequestDto addressRequestDto = new AddressRequestDto();

        addressRequestDto.setEmail(requestDto.getEmail());
        addressRequestDto.setAddress1(requestDto.getAddress1());
        addressRequestDto.setAddress2(requestDto.getAddress2());
        addressRequestDto.setNickname("기본 배송지");
        addressRequestDto.setIsDefault("Y");

        AddressService addressService = new AddressService(addressRepository);
        addressService.save(addressRequestDto);

        return memberResponseDto;
    }

    public TokenDto login(MemberRequestDto requestDto) {
        UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();

        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);

        return tokenProvider.generateTokenDto(authentication);
    }

}