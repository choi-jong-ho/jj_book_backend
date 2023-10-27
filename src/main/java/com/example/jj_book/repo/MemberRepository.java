package com.example.jj_book.repo;

import com.example.jj_book.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

//    Optional<Member> findById(Long id);

    Member findByEmail(String email);

    Member findByEmailAndUseYn(String email, String useYn);

    Optional<Member> findMemberByEmail(String email);
}
