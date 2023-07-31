package com.example.jj_book.repo;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Table(name = "address")
@Entity
@Builder
@DynamicInsert
public class Address {
    @Id
    @Column(name = "addressno", nullable = false)
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long addressNo;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "address1", nullable = false)
    private String address1;

    @Column(name = "address2", nullable = false)
    private String address2;

    @Column(name = "isdefault", nullable = false)
    private String isDefault;

    @Builder
    public Address(Long addressNo, String email, String nickname, String address1, String address2, String isDefault){
        this.addressNo = addressNo;
        this.email = email;
        this.nickname = nickname;
        this.address1 = address1;
        this.address2 = address2;
        this.isDefault = isDefault;
    }
}