package cn.com.filter.token;

import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@Log4j
@RunWith(SpringRunner.class)
public class JWTUtilsTest {
    private MockHttpServletRequest request;

    @Test
    public void test(){
    }
}