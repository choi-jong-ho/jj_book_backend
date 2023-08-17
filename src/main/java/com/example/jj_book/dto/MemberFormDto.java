package com.example.jj_book.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class MemberFormDto {

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "아이디를 입력해주세요.")
    @Email
    private String email;

    private String password;

    @NotBlank(message = "휴대폰번호를 입력해주세요.")
    private String phone;

    private String useYn;
}
