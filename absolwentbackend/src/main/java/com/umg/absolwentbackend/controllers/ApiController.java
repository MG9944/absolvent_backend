package com.umg.absolwentbackend.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class ApiController {

    @RequestMapping("/")
    public String getHelloWorld() {
        return "Hello world";
    }
}
