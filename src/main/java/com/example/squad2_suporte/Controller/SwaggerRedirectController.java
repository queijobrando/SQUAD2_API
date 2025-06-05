package com.example.squad2_suporte.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwaggerRedirectController {
    @GetMapping("/swagger-ui")
    public String redirectToCustomSwagger() {
        return "forward:/swagger-ui/index.html";
    }
}