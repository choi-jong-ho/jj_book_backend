package com.example.jj_book.service;

import com.example.jj_book.dto.AddressRequestDto;
import com.example.jj_book.dto.AddressResponseDto;
import com.example.jj_book.repo.Address;
import com.example.jj_book.repo.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressResponseDto save(AddressRequestDto addressRequestDto) {
        if (addressRepository.existsByAddress1(addressRequestDto.getEmail())) {
            throw new RuntimeException("이미 등록되어 있는 주소입니다");
        }

        System.out.println("getEmail : "+addressRequestDto.getEmail());
        System.out.println("getAddress1 : "+addressRequestDto.getAddress1());
        System.out.println("getAddress2 : "+addressRequestDto.getAddress2());
        System.out.println("getNickname : "+addressRequestDto.getNickname());
        System.out.println("getIsDefault : "+addressRequestDto.getIsDefault());

        Address address = addressRequestDto.toAddress();
        return AddressResponseDto.of(addressRepository.save(address));
    }
}
