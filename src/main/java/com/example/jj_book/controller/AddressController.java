package com.example.jj_book.controller;

import com.example.jj_book.dto.AddressDto;
import com.example.jj_book.entity.Address;
import com.example.jj_book.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @GetMapping(value = {"/list","/list/{page}"})
    public List<Page> addressList(@PathVariable("page") Optional<Integer> page, Principal principal){

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        Page<Address> addrList = addressService.getAddrList(principal.getName(), pageable);

        List<Page> list = new ArrayList<>();
        list.add(addrList);

        return list;
    }

    @GetMapping(value = "/{addressId}")
    public ResponseEntity addressDtl(@PathVariable("id") Long addressId){

        AddressDto addressDto = addressService.getAddrDtl(addressId);

        return ResponseEntity.ok(addressDto);
    }

    @PostMapping(value = "/{addressId}")
    public ResponseEntity addrUpdate(@RequestBody @Valid AddressDto addressDto, BindingResult bindingResult, Principal principal){
        if(bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        }

        try {
            addressService.updateAddr(addressDto, principal.getName());
        } catch (Exception e){
        }

        return ResponseEntity.ok(addressDto);
    }

    @PostMapping(value = "/delete")
    public ResponseEntity addrDelete(@RequestBody AddressDto addressDto){

        try {
            addressService.deleteAddr(addressDto);
        } catch (Exception e){
        }

        return ResponseEntity.ok(addressDto);
    }
}
