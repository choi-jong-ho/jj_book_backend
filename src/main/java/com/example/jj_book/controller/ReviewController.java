package com.example.jj_book.controller;

import com.example.jj_book.dto.ReviewFormDto;
import com.example.jj_book.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;


    @PostMapping(value = "/new")
    public ResponseEntity reviewNew(@Valid ReviewFormDto reviewFormDto, BindingResult bindingResult,
                                    @RequestPart(value = "reviewImgFile", required = false) List<MultipartFile> reviewImgFileList,
                                    Principal principal) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        }

        try {
            reviewService.saveReview(reviewFormDto, reviewImgFileList, principal.getName());
        } catch (Exception e) {

        }

        return ResponseEntity.ok(reviewFormDto);
    }
}