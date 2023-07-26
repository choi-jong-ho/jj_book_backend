package com.example.jj_book.controller;

import com.example.jj_book.entity.CrudEntity;
import com.example.jj_book.repository.CurdEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class CrudController {

    private  final CurdEntityRepository curdEntityRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("search")
    public String searchAllMember(){
        return curdEntityRepository.findAll().toString();
    }

    @GetMapping("insert")
    public String insertMember(@RequestParam(value = "user_name") String user_name, @RequestParam(value = "age") int age,
                               @RequestParam(value = "id") String id, @RequestParam(value = "password") String password){
        if(curdEntityRepository.findById(user_name).isPresent()){
            return "동일한 이름이 있습니다.";
        }else{
            CrudEntity crudEntity = CrudEntity.builder().user_name(user_name).age(age).id(id).password(password).build();
            curdEntityRepository.save(crudEntity);
            return "이름 : " + user_name + " 나이 : " + age + " 아이디 : " + id + " 비밀번호 : " + password;
        }
    }
}
