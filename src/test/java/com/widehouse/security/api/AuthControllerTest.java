package com.widehouse.security.api;

import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getUsers() throws Exception {
        mockMvc.perform(get("/users")
                .with(httpBasic("user", "test"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Sql("classpath:data/user-data.sql")
    @Test
    void getAuthToken_WithValidPassword_ThenGetToken() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", "foo");
        params.add("username", "user@bar.com");
        params.add("password", "password");
        params.add("scope", "read");

        mockMvc.perform(post("/oauth/token")
                .with(httpBasic("foo", "bar"))
                .params(params)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void encode_password() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println("encoded password = " + passwordEncoder.encode("password"));
        System.out.println("encoded bar = " + passwordEncoder.encode("bar"));

        then(passwordEncoder.matches("password", "$2a$10$4yjGFL/JYxRHgT2HZa913.hDzK/ZRb5EXRrOFWaRoUexO71sKO4sW"))
                .isTrue();

    }

}