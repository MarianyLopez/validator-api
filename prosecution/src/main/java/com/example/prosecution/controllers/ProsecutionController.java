package com.example.prosecution.controllers;

import com.example.prosecution.services.ProsecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/prosecution")
public class ProsecutionController {

    private final ProsecutionService prosecutionService;

    @Autowired()
    public ProsecutionController(ProsecutionService prosecutionService) {
        this.prosecutionService = prosecutionService;
    }

    @PostMapping()
    public String readFile(@RequestBody String filePath){
       return prosecutionService.readFile(filePath);
    }
}
