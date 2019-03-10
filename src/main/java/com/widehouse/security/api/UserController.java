package com.widehouse.security.api;

import com.widehouse.security.auth.CurrentUser;
import com.widehouse.security.user.model.User;
import com.widehouse.security.user.service.UserService;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("users")
@RestController
public class UserController {
    private final UserService userService;

    @GetMapping("")
    public List<User> listAll() {
        return userService.listAllUsers();
    }

    @GetMapping("me")
    public User getUser(@CurrentUser String username) {
        return userService.getUserByEmail(username);
    }
}
