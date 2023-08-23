package com.example.jj_book.service;

import com.example.jj_book.entity.ReviewImg;
import com.example.jj_book.repo.ReviewImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewImgService {

    @Value("${reviewImgLocation}")
    private String reviewImgLocation;

    private final ReviewImgRepository reviewImgRepository;

    private final FileService fileService;

    public void saveReviewImg(ReviewImg reviewImg, MultipartFile reviewImgFile) throws Exception{
        String oriImgName = reviewImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        //파일 업로드
        if(!StringUtils.isEmpty(oriImgName)){
            imgName = fileService.uploadFile(reviewImgLocation, oriImgName,
                    reviewImgFile.getBytes());
            imgUrl = "/images/review/" + imgName;
        }

        //상품 이미지 정보 저장
        reviewImg.updateReviewImg(oriImgName, imgName, imgUrl);
        reviewImgRepository.save(reviewImg);
    }

    public void updateReviewImg(Long reviewImgId, MultipartFile reviewImgFile) throws Exception{
        if(!reviewImgFile.isEmpty()){
            ReviewImg savedReviewImg = reviewImgRepository.findById(reviewImgId)
                    .orElseThrow(EntityNotFoundException::new);

            //기존 이미지 파일 삭제
            if(!StringUtils.isEmpty(savedReviewImg.getImgName())) {
                fileService.deleteFile(reviewImgLocation+"/"+
                        savedReviewImg.getImgName());
            }

            String oriImgName = reviewImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(reviewImgLocation, oriImgName, reviewImgFile.getBytes());
            String imgUrl = "/images/review/" + imgName;
            savedReviewImg.updateReviewImg(oriImgName, imgName, imgUrl);
        }
    }

}