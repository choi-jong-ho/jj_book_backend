package com.example.jj_book.controller;

import com.example.jj_book.service.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@RestController
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/like/{reviewId}")
    public ResponseEntity addLike(@PathVariable Long reviewId, Principal principal) {

        boolean result = false;

        if (principal.getName() != null) {
            result = likeService.addLike(principal.getName(), reviewId);
        }

        return result ? new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/like/cancel/{reviewId}")
    public ResponseEntity cancelLike(@PathVariable Long reviewId, Principal principal) {

        boolean result = false;

        if (principal.getName() != null) {
            result = likeService.cancelLike(principal.getName(), reviewId);
        }

        return result ? new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/notlike/{reviewId}")
    public ResponseEntity addNotLike(@PathVariable Long reviewId, Principal principal) {

        boolean result = false;

        if (principal.getName() != null) {
            result = likeService.addNotLike(principal.getName(), reviewId);
        }

        return result ? new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/notlike/cancel/{reviewId}")
    public ResponseEntity cancelNotLike(@PathVariable Long reviewId, Principal principal) {

        boolean result = false;

        if (principal.getName() != null) {
            result = likeService.cancelNotLike(principal.getName(), reviewId);
        }

        return result ? new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}