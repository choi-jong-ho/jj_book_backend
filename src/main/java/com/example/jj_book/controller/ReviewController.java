package com.example.jj_book.controller;

import com.example.jj_book.dto.ReviewFormDto;
import com.example.jj_book.dto.ReviewHistDto;
import com.example.jj_book.dto.ReviewItemDto;
import com.example.jj_book.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @PostMapping(value = "/update")
    public ResponseEntity reviewUpdate(@Valid ReviewFormDto reviewFormDto, BindingResult bindingResult,
                                    @RequestPart(value = "reviewImgFile", required = false) List<MultipartFile> reviewImgFileList,
                                    Principal principal) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        }

        try {
            reviewService.updateReview(reviewFormDto, reviewImgFileList, principal.getName());
        } catch (Exception e) {

        }

        return ResponseEntity.ok(reviewFormDto);
    }

    @PostMapping(value = "/delete")
    public ResponseEntity reviewDelete(@RequestBody ReviewFormDto reviewFormDto) {


        try {
            reviewService.deleteReview(reviewFormDto);
        } catch (Exception e) {
        }

        return ResponseEntity.ok(reviewFormDto);
    }

    //리뷰 이력 조회
    @GetMapping(value = {"/list", "/list/{page}"})
    public List<Page> reviewHist(@PathVariable("page") Optional<Integer> page, Principal principal){

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        Page<ReviewHistDto> reviewHistDtoList = reviewService.getReviewList(principal.getName(), pageable);

        List<Page> list = new ArrayList<>();
        list.add(reviewHistDtoList);

        return list;
    }

    @GetMapping(value = {"/listAll", "/listAll/{page}"})
    public List<Page> reviewList(ReviewFormDto reviewFormDto, @PathVariable("page") Optional<Integer> page, Principal principal){

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        Page<ReviewItemDto> reviewItemDtoList = reviewService.getReviewAllList(reviewFormDto.getItemId(), pageable);

        List<Page> list = new ArrayList<>();
        list.add(reviewItemDtoList);

        return list;
    }
}