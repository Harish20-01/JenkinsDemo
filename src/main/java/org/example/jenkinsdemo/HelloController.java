package org.example.jenkinsdemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String home() {
        return "Hello from Spring Boot CI/CD Demo!";
    }

    @GetMapping("/health")
    public String health() {
        return "UP";
    }
}
