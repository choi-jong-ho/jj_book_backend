package com.example.jj_book.repo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
@Table(name = "USER")
@DynamicInsert
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

    @Transient
    private String address1;
    @Transient
    private String address2;

    public void setUserName(String userName){
        this.userName = userName;
    }

    public void setPassword(String password){
        this.password = password;
    }

    @Builder
    public Member(Long id, String userName, String password, String email,  String phone, String address1, String address2, String grade, LocalDateTime regDt, LocalDateTime upDt, Role role){
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.address1 = address1;
        this.address2 = address2;
        this.grade = grade;
        this.regDt = regDt;
        this.upDt = upDt;
        this.role = role;
    }
}