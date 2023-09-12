package com.example.jj_book.service;

import com.example.jj_book.dto.MemberFormDto;
import com.example.jj_book.entity.Address;
import com.example.jj_book.entity.Member;
import com.example.jj_book.repo.AddressRepository;
import com.example.jj_book.repo.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final AddressRepository addressRepository;

    public void saveMember(Member member, MemberFormDto memberFormDto){
        validateDuplicateMember(member);
        memberRepository.save(member);

        Address address = new Address();
        address.setMember(member);

        address.updateAddress(memberFormDto.getAddrCategory(), memberFormDto.getPostcode(), memberFormDto.getAddress(), memberFormDto.getAddressDetail(), memberFormDto.getRepAddYn());
        addressRepository.save(address);
    }

    private void validateDuplicateMember(Member member){
        Member findMember = memberRepository.findByEmail(member.getEmail());
        if(findMember != null){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmailAndUseYn(email, "Y");

        if(member == null){
            throw new UsernameNotFoundException(email);
        }

        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }

    public Long deleteMemeber(MemberFormDto memberFormDto) throws Exception{

        //아이디 삭제
        Member member = memberRepository.findByEmail(memberFormDto.getEmail());

        if (member == null){
            throw new UsernameNotFoundException(memberFormDto.getEmail());
        }

        member.deleteMember(memberFormDto);

        return member.getId();
    }

    public Long updateMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) throws Exception{

        //정보 수정
        Member member = memberRepository.findByEmail(memberFormDto.getEmail());

        if (member == null){
            throw new UsernameNotFoundException(memberFormDto.getEmail());
        }

        member.updateMember(memberFormDto, passwordEncoder);
        memberRepository.save(member);

        return member.getId();
    }

    public Member getMember(String email) throws Exception{

        Member member = memberRepository.findByEmail(email);

        if (member == null){
            throw new UsernameNotFoundException(email);
        }

        return member;
    }

}
