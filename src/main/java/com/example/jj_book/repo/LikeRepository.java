package com.example.jj_book.repo;

import com.example.jj_book.entity.Like;
import com.example.jj_book.entity.Member;
import com.example.jj_book.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByMemberAndReview(Member member, Review review);
}