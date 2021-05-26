package com.moon2.moon2_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Moon2BeApplication {

    public static void main(String[] args) {
        SpringApplication.run(Moon2BeApplication.class, args);
    }

    @GetMapping
    public String hello(){
        return "Juanfer PUTO";
    }
}
