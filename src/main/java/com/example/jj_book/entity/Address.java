package com.example.jj_book.entity;

import com.example.jj_book.dto.AddressDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="ADDRESS")
@Getter
@Setter
public class Address extends BaseEntity{
    @Id
    @Column(name="address_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="addr_category")
    private String addrCategory;

    private String postcode; //우편변호

    private String address; //주소

    private String addressDetail; //상세주소

    @Column(name="rep_add_yn")
    private String repAddYn; //대표 주소 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void updateAddress(String addrCategory, String postcode, String address, String addressDetail, String repAddYn){
        this.addrCategory = addrCategory;
        this.postcode = postcode;
        this.address = address;
        this.addressDetail = addressDetail;
        this.repAddYn = repAddYn;
    }

    public void updateAddr(AddressDto addressDto){
        this.addrCategory = addressDto.getAddrCategory();
        this.postcode = addressDto.getPostcode();
        this.address = addressDto.getAddress();
        this.addressDetail = addressDto.getAddressDetail();
        this.repAddYn = addressDto.getRepAddYn();
    }

}
