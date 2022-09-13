package com.example.assignment;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = AssignmentApplication.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.yaml")
@ActiveProfiles("test")
public @interface AssignmentSpringTest {}
