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

import javax.persistence.EntityNotFoundException;
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
        address.updateAddress(addressDto.getAddrCategory(), addressDto.getPostcode(), addressDto.getAddress(), addressDto.getAddressDetail(), addressDto.getRepAddYn());
        addressRepository.save(address);
    }

    @Transactional(readOnly = true)
    public Page<Address> getAddrList(String email, Pageable pageable){

        Member member = memberRepository.findByEmail(email);
        List<Address> addrs = addressRepository.findAddrs(member.getId(), pageable);
        Long totalCount = addressRepository.countAddr(member.getId());


        return new PageImpl<Address>(addrs, pageable, totalCount);
    }

    @Transactional(readOnly = true)
    public AddressDto getAddrDtl(Long addrId){

        Address address = addressRepository.findById(addrId)
                .orElseThrow(EntityNotFoundException::new);
        AddressDto addressDto = AddressDto.of(address);
        return addressDto;
    }

    public Long updateAddr(AddressDto addressDto, String email) throws Exception{

        //기본배송지여부 전체 N 업데이트
        addressRepository.updateRepYn(email);

        //배송지 수정
        Address address = addressRepository.findById(addressDto.getId())
                .orElseThrow(EntityNotFoundException::new);
        address.updateAddr(addressDto);

        addressRepository.save(address);

        return address.getId();
    }

    public void deleteAddr(AddressDto addressDto) throws Exception {

        Address address = addressRepository.findById(addressDto.getId())
                .orElseThrow(EntityNotFoundException::new);

        if(address.getRepAddYn().equals("N")){
            //주소 삭제
            addressRepository.deleteById(addressDto.getId());
        }else {
            throw new Exception();
        }

    }
}
