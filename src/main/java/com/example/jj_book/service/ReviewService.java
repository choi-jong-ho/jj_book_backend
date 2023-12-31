package com.example.jj_book.service;

import com.example.jj_book.dto.*;
import com.example.jj_book.entity.*;
import com.example.jj_book.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewItemRepository reviewItemRepository;
    private final ReviewImgRepository reviewImgRepository;
    private final ReviewImgService reviewImgService;
    private final LikeRepository likeRepository;
    private final NotLikeRepository notLikeRepository;

    public Long saveReview(ReviewFormDto reviewFormDto, List<MultipartFile> reviewImgFileList, String email) throws Exception{

        Item item = itemRepository.findById(reviewFormDto.getItemId())
                .orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findByEmail(email);

        List<ReviewItem> reviewItemList = new ArrayList<>();
        ReviewItem reviewItem = ReviewItem.createReviewItem(item, reviewFormDto);
        reviewItemList.add(reviewItem);
        Review review = Review.createReview(member, reviewItemList);
        reviewRepository.save(review);

//        if (reviewImgFileList.size() > 0) {
//            //리뷰 이미지
//            for(int i=0;i<reviewImgFileList.size();i++){
//                ReviewImg reviewImg = new ReviewImg();
//                reviewImg.setReview(review);
//
//                reviewImgService.saveReviewImg(reviewImg, reviewImgFileList.get(i));
//            }
//        }

        return review.getId();
    }

    public Long updateReview(ReviewFormDto reviewFormDto, List<MultipartFile> reviewImgFileList, String email) throws Exception{

        ReviewItem reviewItem = reviewItemRepository.findByReviewId(reviewFormDto.getId());

        reviewItem.updateReviewItem(reviewFormDto);

//        if (reviewImgFileList.size() > 0) {
//            //리뷰 이미지
//            for(int i=0;i<reviewImgFileList.size();i++){
//                ReviewImg reviewImg = new ReviewImg();
//                reviewImg.setReview(review);
//
//                reviewImgService.saveReviewImg(reviewImg, reviewImgFileList.get(i));
//            }
//        }

        return reviewItem.getId();
    }

    public void deleteReview(ReviewFormDto reviewFormDto) throws Exception{

        reviewRepository.deleteById(reviewFormDto.getId());
    }

    @Transactional(readOnly = true)
    public Page<ReviewHistDto> getReviewList(String email, Pageable pageable){

        List<Review> reviews = reviewRepository.findReviewList(email, pageable);
        Long totalCount = reviewRepository.countReview(email);

        List<ReviewHistDto> reviewHistDtos = new ArrayList<>();

        for(Review review : reviews) {
            ReviewHistDto reviewHistDto = new ReviewHistDto(review);
            List<ReviewItem> reviewItemList = review.getReviewItemList();
            for(ReviewItem reviewItem : reviewItemList){
                ReviewImg reviewImg = reviewImgRepository.findByReviewId(review.getId());
                if(reviewImg != null){
                    ReviewItemDto reviewItemDto = new ReviewItemDto(reviewItem, reviewImg.getImgUrl());
                    reviewHistDto.addReviewItemDto(reviewItemDto);
                }else{
                    Long likeCount = likeRepository.likeCount(reviewItem.getReview().getId());
                    Long notLikeCount = notLikeRepository.notlikeCount(reviewItem.getReview().getId());
                    ReviewItemDto reviewItemDto = new ReviewItemDto(reviewItem, likeCount, notLikeCount);
                    reviewHistDto.addReviewItemDto(reviewItemDto);
                }
            }

            reviewHistDtos.add(reviewHistDto);
        }

        return new PageImpl<ReviewHistDto>(reviewHistDtos, pageable, totalCount);
    }

    @Transactional(readOnly = true)
    public Page<ReviewItemDto> getReviewAllList(Long itemId, Pageable pageable, String email){

        List<ReviewItem> reviewItems = reviewItemRepository.findReviewAllList(itemId, pageable);
        Long totalCount = reviewItemRepository.countAllReview(itemId);
        Member member = memberRepository.findByEmail(email);
        boolean likeYn = false;

        List<ReviewItemDto> reviewItemDtoList = new ArrayList<>();

        for(ReviewItem reviewItem : reviewItems) {
            Like like = likeRepository.findLike(member.getId(), reviewItem.getReview().getId());
            if (like != null) {
                likeYn = true;
            }
            Long likeCount = likeRepository.likeCount(reviewItem.getReview().getId());
            Long notLikeCount = notLikeRepository.notlikeCount(reviewItem.getReview().getId());
            ReviewItemDto reviewItemDto = new ReviewItemDto(reviewItem, likeCount, notLikeCount, likeYn);
            reviewItemDtoList.add(reviewItemDto);
        }

        return new PageImpl<ReviewItemDto>(reviewItemDtoList, pageable, totalCount);
    }

    @Transactional(readOnly = true)
    public Page<ReviewItemDto> getReviewAllList(Long itemId, Pageable pageable){

        List<ReviewItem> reviewItems = reviewItemRepository.findReviewAllList(itemId, pageable);
        Long totalCount = reviewItemRepository.countAllReview(itemId);

        List<ReviewItemDto> reviewItemDtoList = new ArrayList<>();

        for(ReviewItem reviewItem : reviewItems) {
            Long likeCount = likeRepository.likeCount(reviewItem.getReview().getId());
            Long notLikeCount = notLikeRepository.notlikeCount(reviewItem.getReview().getId());
            ReviewItemDto reviewItemDto = new ReviewItemDto(reviewItem, likeCount, notLikeCount);
            reviewItemDtoList.add(reviewItemDto);
        }

        return new PageImpl<ReviewItemDto>(reviewItemDtoList, pageable, totalCount);
    }
}