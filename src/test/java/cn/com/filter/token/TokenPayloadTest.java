package cn.com.filter.token;

import lombok.extern.log4j.Log4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;


@Log4j
@RunWith(SpringRunner.class)
@SpringBootTest

class TokenPayloadTest {
    private MockHttpServletRequest request;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void main() {
        log.info(request);
    }
}