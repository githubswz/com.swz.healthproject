package com.swz.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public String add(){
        System.out.println("add...");
        return "success";
    }

    @RequestMapping("/delete")
    @PreAuthorize("hasAuthority('delete')")
    public String delete(){
        System.out.println("delete...");
        return "success";
    }

    @RequestMapping("/hasRole")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String hasRole(){
        System.out.println("hasrole");
        return "success";
    }
}