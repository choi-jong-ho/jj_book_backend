package com.example.jj_book.dto;

import com.example.jj_book.repo.Address;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressRequestDto {

    private String email;
    private String nickname;
    private String address1;
    private String address2;
    private String isDefault;

    public Address toAddress() {
        return Address.builder()
                .email(email)
                .nickname(nickname)
                .address1(address1)
                .address2(address2)
                .isDefault(isDefault)
                .build();
    }
}