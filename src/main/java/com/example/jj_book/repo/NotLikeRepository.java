package com.example.jj_book.repo;

import com.example.jj_book.entity.Member;
import com.example.jj_book.entity.NotLike;
import com.example.jj_book.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface NotLikeRepository extends JpaRepository<NotLike, Long> {
    Optional<NotLike> findByMemberAndReview(Member member, Review review);

    @Query("select l from NotLike l where l.member.id = :memberId and l.review.id = :reviewId")
    NotLike findNotLike(@Param("memberId") Long memberId, @Param("reviewId")Long reviewId);
}