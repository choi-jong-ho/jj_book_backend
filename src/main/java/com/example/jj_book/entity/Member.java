package com.example.jj_book.entity;

import com.example.jj_book.dto.MemberFormDto;
import com.example.jj_book.repo.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
@DynamicInsert
public class Member {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String phone;

    @Column(columnDefinition = "varchar(50) default 'BRONZE'")
    private String grade;

    @Column(name = "use_yn", columnDefinition = "char(1) default 'Y'")
    private String useYn;

    @Column(name = "reg_dt", columnDefinition = "datetime default NOW()")
    private LocalDateTime regDt;

    @Column(name = "up_dt", columnDefinition = "datetime default NOW()")
    private LocalDateTime upDt;

    @Enumerated(EnumType.STRING)
    private Role role;

    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder){
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setPhone(memberFormDto.getPhone());
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);
        member.setRole(Role.USER);

        return member;
    }

}