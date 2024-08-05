package com.example.security.controller;

import com.example.security.model.Employee;
import com.example.security.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepositary;

    @GetMapping("/addUser")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String addUser(){
        Employee user1 = Employee.builder().name("sonu").userId(1).salary(100000).build();
        Employee user2 = Employee.builder().name("nitesh").userId(2).salary(200000).build();
        Employee user3 = Employee.builder().name("sachin").userId(3).salary(300000).build();
        Employee user4 = Employee.builder().name("hari").userId(4).salary(400000).build();
        userRepositary.saveAll(List.of(user1,user2,user3,user4));
        return "User added successfully";
    }

    @GetMapping("msg")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String getMsg(){

        return "User retrieving successfully";
    }
}
