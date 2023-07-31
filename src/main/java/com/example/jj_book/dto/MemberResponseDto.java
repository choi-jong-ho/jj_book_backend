package com.example.jj_book.dto;

import com.example.jj_book.repo.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResponseDto {
    private String email;
    private String userName;
    private String phone;
    private String grade;

    public static MemberResponseDto of(Member member) {
        return MemberResponseDto.builder()
                .email(member.getEmail())
                .userName(member.getUserName())
                .phone(member.getPhone())
                .grade(member.getGrade())
                .build();
    }
}