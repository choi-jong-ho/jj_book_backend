package com.example.jj_book.controller;

import com.example.jj_book.dto.CartItemDto;
import com.example.jj_book.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping(value = "/new")
    public @ResponseBody ResponseEntity order(@RequestBody @Valid CartItemDto cartItemDto
            , BindingResult bindingResult, Principal principal) {

        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for(FieldError fieldError : fieldErrors){
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }
        String email = principal.getName();
        Long cartItemid;

        try {
            cartItemid = cartService.addCart(cartItemDto, email);
        } catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(cartItemid, HttpStatus.OK);
    }

//    @GetMapping(value = "/list")
//    public String orderHist(Principal principal, Model model){
//        List<CartDetailDto> cartDetailList = cartService.getCartList(principal.getName());
//        model.addAttribute("cartItems", cartDetailList);
//        return "cart/cartList";
//    }
}