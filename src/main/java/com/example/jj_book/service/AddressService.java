package com.example.jj_book.service;

import com.example.jj_book.dto.AddressDto;
import com.example.jj_book.entity.Address;
import com.example.jj_book.entity.Member;
import com.example.jj_book.repo.AddressRepository;
import com.example.jj_book.repo.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        address.updateAddress(address.getAddrCategory(), addressDto.getPostcode(), addressDto.getAddress(), addressDto.getAddressDetail(), addressDto.getRepAddYn());
        addressRepository.save(address);
    }

    @Transactional(readOnly = true)
    public Page<Address> getAddrList(String email, Pageable pageable){

        Member member = memberRepository.findByEmail(email);
        List<Address> addrs = addressRepository.findAddrs(member.getId(), pageable);
        Long totalCount = addressRepository.countCart(member.getId());


        return new PageImpl<Address>(addrs, pageable, totalCount);
    }
}
