package com.example.jj_book.entity;

import com.example.jj_book.dto.ReviewFormDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="review_item")
public class ReviewItem extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "review_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="review_id")
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private String contents; // 리뷰 내용

    private Long rating; //별점

    public static ReviewItem createReviewItem(Item item, ReviewFormDto reviewFormDto){
        ReviewItem reviewItem = new ReviewItem();
        reviewItem.setItem(item);
        reviewItem.setContents(reviewFormDto.getContents());
        reviewItem.setRating(reviewFormDto.getRating());
        return reviewItem;
    }

    public void updateReviewItem(ReviewFormDto reviewFormDto){
        this.contents = reviewFormDto.getContents();
        this.rating = reviewFormDto.getRating();
    }
}