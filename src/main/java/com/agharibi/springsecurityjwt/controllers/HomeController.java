package com.agharibi.springsecurityjwt.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping({"/index"})
    public String index() {
        return "Welcome to Spring Security JWT";
    }
}
