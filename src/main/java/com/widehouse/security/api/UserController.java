package com.widehouse.security.api;

import com.widehouse.security.user.model.User;
import com.widehouse.security.user.service.UserService;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    public List<User> listAll() {
        return userService.listAllUsers();
    }
}
