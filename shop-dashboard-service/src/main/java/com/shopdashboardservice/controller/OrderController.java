package com.shopdashboardservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @GetMapping("/list")
    public static ResponseEntity<String> getOrders(){

        return new ResponseEntity("Hello", HttpStatus.OK);
    }
}
