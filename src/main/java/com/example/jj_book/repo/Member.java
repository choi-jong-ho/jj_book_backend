package com.example.jj_book.repo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
@Table(name = "USER")
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "email",nullable = false, unique = true)
    private String email;

    @Column(name = "address",nullable = false)
    private String address;

    @Column(name = "phone",nullable = false)
    private String phone;

    @Column(name = "grade",nullable = false)
    private String grade;

    @CreationTimestamp
    @Column(name = "reg_dt")
    private LocalDateTime regDt;

    @UpdateTimestamp
    @Column(name = "up_dt")
    private LocalDateTime upDt;

    @Enumerated(EnumType.STRING)
    private Role role;

    public void setUserName(String userName){
        this.userName = userName;
    }

    public void setPassword(String password){
        this.password = password;
    }

    @Builder
    public Member(Long id, String userName, String password, String email, String address, String phone, String grade, LocalDateTime regDt, LocalDateTime upDt, Role role){
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.grade = grade;
        this.regDt = regDt;
        this.upDt = upDt;
        this.role = role;
    }
}