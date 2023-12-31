package com.example.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/validation")
public class ValidationController {
    private final ValidationService validationService;

    @Autowired()
    public ValidationController(ValidationService validationService) {
        this.validationService = validationService;
    }

    @PostMapping("/csv")
    public boolean validationFileCSV(@RequestBody String[] strings){
       return validationService.validationFileCSV(strings);
    }
    @PostMapping("/excel")
    public boolean validationFileExcel(@RequestBody String[] strings){
        return validationService.validationFileExcel(strings);
    }

}
