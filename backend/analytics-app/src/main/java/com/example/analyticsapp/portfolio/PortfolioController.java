package com.example.analyticsapp.portfolio;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {

    @GetMapping
    public String portfolioHello() {
        return "Hello Portfolio!";
    }

}
