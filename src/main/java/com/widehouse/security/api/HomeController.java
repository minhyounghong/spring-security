package com.widehouse.security.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "";
    }

    @GetMapping("/hello")
    public String listUsers() {
        return "users";
    }

//    @GetMapping("/users/me")
//    public String getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
//
//        return userPrincipal.getName();
//    }
}
