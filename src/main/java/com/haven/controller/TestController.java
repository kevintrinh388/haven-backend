package com.haven.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    // TODO?: remove this file
    @GetMapping
    public String test() { return "test"; }

    @GetMapping("/secure")
    public String secure() {
        return "You are authenticated!";
    }
}
