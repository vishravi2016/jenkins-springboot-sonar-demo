package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String sayHello(){
        int x=10,y=20;
        int z=x+y;
        return "Welcome to DevOps world!";
    }
}
