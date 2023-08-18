package com.example.jj_book.controller;

import com.example.jj_book.dto.MemberFormDto;
import com.example.jj_book.entity.Member;
import com.example.jj_book.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberControllerTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PasswordEncoder passwordEncoder;


    public void createMember(String email, String password){
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail(email);
        memberFormDto.setName("테스트");
        memberFormDto.setPhone("01012341234");
        memberFormDto.setPassword(password);

        Member member = new Member();

        memberService.saveMember(member, memberFormDto);
    }

//    @Test
//    @DisplayName("로그인 성공 테스트")
//    public void loginSuccessTest() throws Exception{
//        String email = "test@test.com";
//        String password = "test";
//        this.createMember(email, password);
//
//        mockMvc.perform(formLogin().userParameter("email")
//                .loginProcessingUrl("/member/login")
//                .user(email).password(password))
//                .andExpect(SecurityMockMvcResultMatchers.authenticated());
//    }

    @Test
    @DisplayName("로그인 실패 테스트")
    public void loginFailTest() throws Exception{
        String email = "test@email.com";
        String password = "1234";
        this.createMember(email, password);
        mockMvc.perform(formLogin().userParameter("email")
                        .loginProcessingUrl("/member/login")
                        .user(email).password("test1"))
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated());
    }

}