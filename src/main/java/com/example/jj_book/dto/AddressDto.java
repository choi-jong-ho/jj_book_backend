package com.example.jj_book.dto;

import com.example.jj_book.entity.Address;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class AddressDto {

    private Long id;

    private String postcode; //우편변호

    private String address; //주소

    private String addressDetail; //상세주소

    private String repAddYn; //대표 주소 여부

    private static ModelMapper modelMapper = new ModelMapper();

    public static AddressDto of(Address address) {
        return modelMapper.map(address,AddressDto.class);
    }

}
