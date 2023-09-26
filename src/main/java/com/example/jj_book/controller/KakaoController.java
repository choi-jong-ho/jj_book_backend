package com.example.jj_book.controller;

import com.example.jj_book.service.KakaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KakaoController {

    @Autowired
    KakaoService kakaoService;

    @RequestMapping("/login/kakao")
    public String kakaoCallBack(@RequestParam(value = "code", required = false) String code) throws Exception{
        System.out.println("#########" + code);

        String access_Token = kakaoService.getAccessToken(code);
        System.out.println("###access_Token#### : " + access_Token);

        String url = "http://localhost:8080/";

        return url;
    }
}
