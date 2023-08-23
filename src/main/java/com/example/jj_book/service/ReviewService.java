package com.example.jj_book.service;

import com.example.jj_book.dto.ReviewFormDto;
import com.example.jj_book.entity.*;
import com.example.jj_book.repo.ItemRepository;
import com.example.jj_book.repo.MemberRepository;
import com.example.jj_book.repo.ReviewItemRepository;
import com.example.jj_book.repo.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final MemberRepository memberRepository;

    private final ItemRepository itemRepository;

    private final ReviewRepository reviewRepository;

    private final ReviewItemRepository reviewItemRepository;

    private final ReviewImgService reviewImgService;

    public Long saveReview(ReviewFormDto reviewFormDto, List<MultipartFile> reviewImgFileList, String email) throws Exception{

        Item item = itemRepository.findById(reviewFormDto.getItemId())
                .orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findByEmail(email);

        Review review = reviewRepository.findByMemberId(member.getId());
        if (review == null) {
            review = Review.createReview(member);
            reviewRepository.save(review);
        }

//        ReviewItem savedReviewItem = reviewItemRepository.findByReviewIdAndItemId(review.getId(), item.getId());

        ReviewItem reviewItem = ReviewItem.createReviewItem(review, item, reviewFormDto);
        reviewItemRepository.save(reviewItem);
        return reviewItem.getId();

    }


//        if (reviewImgFileList.size() > 0) {
//            //리뷰 이미지
//            for(int i=0;i<reviewImgFileList.size();i++){
//                ReviewImg reviewImg = new ReviewImg();
//                reviewImg.setReview(review);
//
//                reviewImgService.saveReviewImg(reviewImg, reviewImgFileList.get(i));
//            }
//        }
}