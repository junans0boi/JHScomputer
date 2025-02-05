package com.example.jhscomputer;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.junit.jupiter.api.Test;

@SpringBootTest
@TestPropertySource(properties = "youtube.api.key=TEST_YOUTUBE_API_KEY")
class JhscomputerApplicationTests {

    @Test
    void contextLoads() {
    }
}