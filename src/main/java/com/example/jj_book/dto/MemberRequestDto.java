package com.example.jj_book.dto;

import com.example.jj_book.repo.Member;
import com.example.jj_book.repo.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberRequestDto {

    @NotBlank(message = "아이디를 입력해주세요.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;

    @NotBlank(message = "이름을 입력해주세요.")
    private String userName;

    @NotBlank(message = "주소를 입력해주세요.")
    private String address;

    @NotBlank(message = "핸드폰 번호를 입력해주세요.")
    private String phone;

    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(email)
                .address(address)
                .phone(phone)
                .password(passwordEncoder.encode(password))
                .userName(userName)
                .role(Role.ROLE_USER)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}