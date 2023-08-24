package com.example.jj_book.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "review")
public class Review extends BaseEntity {

    @Id
    @Column(name = "review_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    private LocalDateTime reviewDate; //리뷰 등록일

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ReviewItem> reviewItemList = new ArrayList<>();


    public void addReviewItem(ReviewItem reviewItem){
        reviewItemList.add(reviewItem);
        reviewItem.setReview(this);
    }

    public static Review createReview(Member member, List<ReviewItem> reviewItemList){
        Review review = new Review();
        review.setMember(member);
        for(ReviewItem reviewItem : reviewItemList){
            review.addReviewItem(reviewItem);
        }

        review.setReviewDate(LocalDateTime.now());

        return review;
    }
}