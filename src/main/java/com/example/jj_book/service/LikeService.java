package com.example.jj_book.service;

import com.example.jj_book.entity.Like;
import com.example.jj_book.entity.Member;
import com.example.jj_book.entity.NotLike;
import com.example.jj_book.entity.Review;
import com.example.jj_book.repo.LikeRepository;
import com.example.jj_book.repo.MemberRepository;
import com.example.jj_book.repo.NotLikeRepository;
import com.example.jj_book.repo.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final NotLikeRepository notLikeRepository;
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;

    public boolean addLike(String email, Long reviewId) {
        Member member = memberRepository.findByEmail(email);

        Review review = reviewRepository.findById(reviewId).orElseThrow();

        //중복 좋아요 방지
        if(isNotAlreadyLike(member, review)) {
            likeRepository.save(new Like(review, member));
            return true;
        }

        return false;
    }

    //사용자가 이미 좋아요 한 게시물인지 체크
    private boolean isNotAlreadyLike(Member member, Review review) {
        return likeRepository.findByMemberAndReview(member, review).isEmpty();
    }

    public boolean cancelLike(String email, Long reviewId) {
        Member member = memberRepository.findByEmail(email);

        Like like = likeRepository.findLike(member.getId(), reviewId);

        likeRepository.delete(like);

        return true;
    }

    public boolean addNotLike(String email, Long reviewId) {
        Member member = memberRepository.findByEmail(email);

        Review review = reviewRepository.findById(reviewId).orElseThrow();

        //중복 싫어요 방지
        if(isNotAlreadyNotLike(member, review)) {
            notLikeRepository.save(new NotLike(review, member));
            return true;
        }

        return false;
    }

    //사용자가 이미 싫어요 한 게시물인지 체크
    private boolean isNotAlreadyNotLike(Member member, Review review) {
        return notLikeRepository.findByMemberAndReview(member, review).isEmpty();
    }

    public boolean cancelNotLike(String email, Long reviewId) {
        Member member = memberRepository.findByEmail(email);

        NotLike notLike = notLikeRepository.findNotLike(member.getId(), reviewId);

        notLikeRepository.delete(notLike);

        return true;
    }
}