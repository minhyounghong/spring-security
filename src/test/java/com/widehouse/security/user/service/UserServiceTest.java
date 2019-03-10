package com.widehouse.security.user.service;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import com.widehouse.security.user.model.User;
import com.widehouse.security.user.model.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private UserService service;
    
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        service = new UserService(userRepository);
    }

    @Test
    void loadUserByUsername_WithMatchedUser_ThenUserDetails() {
        // given
        given(userRepository.findByEmail(anyString()))
                .willReturn(Optional.of(new User(1, "foo@bar.com", "foo", "1234")));
        // when
        UserDetails userDetails = service.loadUserByUsername("foo@bar.com");
        // then
        then(userDetails)
                .hasFieldOrPropertyWithValue("username", "foo@bar.com");
    }

    @Test
    void loadUserByUsername_WithMatchedUser_ThrowUsernameNotFoundException() {
        // given
        given(userRepository.findByEmail(anyString()))
                .willReturn(Optional.empty());
        // when
        thenThrownBy(() -> service.loadUserByUsername("foo@bar.com"))
                .isExactlyInstanceOf(UsernameNotFoundException.class);
    }

    @Test
    void listAllUsers() {
        given(userRepository.findAll())
                .willReturn(Arrays.asList(
                        new User(1, "foo@bar.com", "foo", "1234"),
                        new User(2, "foo2@bar.com", "foo2", "5678")));
        // when
        List<User> users = service.listAllUsers();
        // then
        then(users).extracting("email")
                .contains("foo@bar.com", "foo2@bar.com");
    }

    @Test
    void getUserById_WithExistId_ThenReturnUser() {
        // given
        given(userRepository.findById(anyInt()))
                .willReturn(Optional.of(new User(1, "foo@bar.com", "foo", "1234")));
        // when
        User user = service.getUserById(1);
        // then
        then(user)
                .hasFieldOrPropertyWithValue("id", 1)
                .hasFieldOrPropertyWithValue("email", "foo@bar.com");
    }

    @Test
    void getUserByEmail_WithExistEmail_ThenReturnUser() {
        // given
        given(userRepository.findByEmail(anyString()))
                .willReturn(Optional.of(new User(1, "foo@bar.com", "foo", "1234")));
        // when
        User user = service.getUserByEmail("foo@bar.com");
        // then
        then(user)
                .hasFieldOrPropertyWithValue("id", 1)
                .hasFieldOrPropertyWithValue("email", "foo@bar.com");
    }


}