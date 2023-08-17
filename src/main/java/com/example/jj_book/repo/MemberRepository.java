package com.example.jj_book.repo;

import com.example.jj_book.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmail(String email);

    Member findByEmailAndUseYn(String email, String useYn);
}
