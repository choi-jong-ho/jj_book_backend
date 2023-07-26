package com.example.jj_book.repository;

import com.example.jj_book.entity.CrudEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurdEntityRepository extends JpaRepository<CrudEntity, String>  {
    @Query(value = "select name, age from sample_member where user_name = :user_name", nativeQuery = true)
    List<CrudEntity> searchParamRepo(@Param("user_name") String user_name);
}
