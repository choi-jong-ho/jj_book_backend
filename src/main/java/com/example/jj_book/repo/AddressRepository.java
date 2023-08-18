package com.example.jj_book.repo;

import com.example.jj_book.entity.Address;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("select o from Address o where o.member.id = :id order by o.repAddYn desc")
    List<Address> findAddrs(@Param("id") Long id, Pageable pageable);

    @Query("select count(o) from Address o where o.member.id = :id")
    Long countAddr(@Param("id") Long id);

    @Query("update Address o set o.repAddYn = 'N' where o.id != :id and o.member.email = :email")
    Address updateRepYn(@Param("id") Long id, @Param("email") String email);

}