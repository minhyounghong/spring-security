package com.widehouse.security.user.model;

import static org.assertj.core.api.BDDAssertions.then;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repository;

    @Test
    void findByEmail_WithEmail_ThenReturnUserOptional() {
        // given
        entityManager.persist(new User(null, "foo@bar.com", "foo", "1234"));
        // when
        Optional<User> result = repository.findByEmail("foo@bar.com");
        // then
        then(result).isPresent();
        then(result.get())
                .hasFieldOrPropertyWithValue("email", "foo@bar.com");
    }
}