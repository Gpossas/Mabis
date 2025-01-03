package com.mabis.repositories;

import com.mabis.domain.user.RegisterUserDTO;
import com.mabis.domain.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;


import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest
{
    @Autowired
    UserRepository user_repository;

    @Test
    void test_find_user_by_email_success()
    {
        String email = "gui@gmail.com";
        RegisterUserDTO dto = new RegisterUserDTO(email, "gui", null, null, "OWNER");
        User user = new User(dto);
        user.setPassword("somepassword");
        user_repository.save(user);

        assertTrue(user_repository.findByEmail(email).isPresent());
    }

    @Test
    void test_find_user_by_email_case_insensitivity()
    {
        RegisterUserDTO dto = new RegisterUserDTO("Gui@gmail.com", "gui", null, null, "OWNER");
        User user = new User(dto);
        user.setPassword("somepassword");
        user_repository.save(user);

        assertTrue(user_repository.findByEmail("gui@gmail.com").isPresent());
    }

    @Test
    void test_find_user_by_email_non_existent_email()
    {
        assertTrue(user_repository.findByEmail("gui@gmail.com").isEmpty());
    }
}
