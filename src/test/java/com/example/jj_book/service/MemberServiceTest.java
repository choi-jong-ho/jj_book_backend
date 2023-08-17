package com.example.jj_book.service;

import com.example.jj_book.dto.MemberFormDto;
import com.example.jj_book.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Member createMember(){
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail("test@test.com");
        memberFormDto.setName("테스트");
        memberFormDto.setPhone("01012341234");
        memberFormDto.setPassword("test");
        return Member.createMember(memberFormDto, passwordEncoder);
    }

//    @Test
//    @DisplayName("회원가입 테스트")
//    public void saveMemberTest(){
//        Member member = createMember();
//        Member saveMember = memberService.saveMember(member);
//
//        assertEquals(member.getEmail(), saveMember.getEmail());
//        assertEquals(member.getName(), saveMember.getName());
//        assertEquals(member.getPhone(), saveMember.getPhone());
//        assertEquals(member.getPassword(), saveMember.getPassword());
//        assertEquals(member.getRole(), saveMember.getRole());
//    }

//    @Test
//    @DisplayName("중복 회원가입 테스트")
//    public void saveDuplicateMemberTest(){
//        Member member1 = createMember();
//        Member member2 = createMember();
//        Member saveMember = memberService.saveMember(member1);
//
//        Throwable e = assertThrows(IllegalStateException.class, () -> {
//            memberService.saveMember(member2);});
//
//        assertEquals("이미 가입된 회원입니다.", e.getMessage());
//
//    }
}