package com.controller;

import com.config.WebSecurityConfig;
import com.repository.UserRepository;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@Import(WebSecurityConfig.class)
public class AbstractControllerTest {

    @MockBean
    private UserRepository userRepository;

}
