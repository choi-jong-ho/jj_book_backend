package com.example.jj_book.repo;

import com.example.jj_book.entity.Like;
import com.example.jj_book.entity.Member;
import com.example.jj_book.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByMemberAndReview(Member member, Review review);

    @Query("select l from Like l where l.member.id = :memberId and l.review.id = :reviewId")
    Like findLike(@Param("memberId") Long memberId, @Param("reviewId")Long reviewId);

    @Query("select count(*) from Like l where l.review.id = :reviewId")
    Long likeCount(@Param("reviewId") Long reviewId);
}