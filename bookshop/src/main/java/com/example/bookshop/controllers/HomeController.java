package com.example.bookshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpServletRequest;

import java.security.Principal;


@Controller
public class HomeController {
    @RequestMapping("/default")
    public String defaultAfterLogin(HttpServletRequest request) {

        return "redirect:/books";
    }

}