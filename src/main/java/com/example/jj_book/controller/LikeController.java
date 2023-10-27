package com.example.jj_book.controller;

import com.example.jj_book.jwt.JwtTokenProvider;
import com.example.jj_book.service.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@RestController
public class LikeController {
    private final LikeService likeService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/like/{reviewId}")
    public ResponseEntity<String> addLike(@PathVariable Long reviewId, HttpServletRequest request) {

        String jwtToken = jwtTokenProvider.resolveToken(request);
        Authentication authentication = jwtTokenProvider.getAuthentication(jwtToken);

        boolean result = false;

        if (authentication.getName() != null) {
            result = likeService.addLike(authentication.getName(), reviewId);
        }

        return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}