package com.example.jj_book.dto;

import com.example.jj_book.repo.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressResponseDto {
    private String email;
    private String nickname;
    private String address1;
    private String address2;
    private String isDefault;

    public static AddressResponseDto of(Address address) {
        return AddressResponseDto.builder()
                .email(address.getEmail())
                .nickname(address.getNickname())
                .address1(address.getAddress1())
                .address2(address.getAddress2())
                .isDefault(address.getIsDefault())
                .build();
    }
}