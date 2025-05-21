package com.example.newapp.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @GetMapping("/")
    public String showLoginPage() {
        logger.info("Accessed login page");
        return "login"; // Renders login.html
    }

    @GetMapping("/home")
    public String showHomePage() {
        logger.info("Accessed home page");
        return "home"; // Renders home.html after login (create home.html)
    }

    @GetMapping("/redirect")
    public String redirectToLogin() {
        logger.info("Redirecting to login page");
        return "redirect:/";
    }
}
