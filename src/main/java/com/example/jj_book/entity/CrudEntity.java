package com.example.jj_book.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="sample_member")
public class CrudEntity {
    @Id
    @Column(nullable = false, unique = true)
    private String user_name;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    private String password;
}
