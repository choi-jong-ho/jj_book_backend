package com.example.jj_book.controller;

import com.example.jj_book.dto.AddressDto;
import com.example.jj_book.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping(value = "/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping(value = "/new")
    public ResponseEntity addressNew(@RequestBody @Valid AddressDto addressDto
            , BindingResult bindingResult, Principal principal){

        if(bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        }

        try {
            addressService.saveAddress(addressDto, principal.getName());
        } catch (Exception e){

        }

        return ResponseEntity.ok(addressDto);
    }
}
