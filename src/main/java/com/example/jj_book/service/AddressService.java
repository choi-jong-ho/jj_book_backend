package com.example.jj_book.service;

import com.example.jj_book.dto.AddressDto;
import com.example.jj_book.entity.Address;
import com.example.jj_book.entity.Member;
import com.example.jj_book.repo.AddressRepository;
import com.example.jj_book.repo.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressService {

    private final AddressRepository addressRepository;

    private final MemberRepository memberRepository;

    public void saveAddress(AddressDto addressDto, String email) throws Exception{

        Member member = memberRepository.findByEmail(email);

        Address address = new Address();
        address.setMember(member);

        //상품 이미지 정보 저장
        address.updateAddress(address.getAddCategory(), addressDto.getPostcode(), addressDto.getAddress(), addressDto.getAddressDetail(), addressDto.getRepAddYn());
        addressRepository.save(address);
    }
}
