package com.manhwakyung.security.api;

import com.manhwakyung.security.auth.CurrentUser;

import java.nio.file.attribute.UserPrincipal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "";
    }

    @GetMapping("/users")
    public String listUsers() {
        return "users";
    }

//    @GetMapping("/users/me")
//    public String getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
//
//        return userPrincipal.getName();
//    }
}
