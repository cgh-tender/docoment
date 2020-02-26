package cn.com.filter.token.Impl;

import cn.com.filter.token.Body.Impl.TokenUserNamePayload;
import lombok.extern.log4j.Log4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@Log4j
@RunWith(SpringRunner.class)
public class MyCustomTest {
    private MockHttpServletRequest request;
    @Resource
    private ToeknFactory toeknFactory;
    @Before
    public void setUp(){
        request = new MockHttpServletRequest();
        request.setCharacterEncoding("UTF-8");
//        response = new MockHttpServletResponse();
    }

    @Test
    public void main() {
        String token = toeknFactory.getInstance().builder(new TokenUserNamePayload("admin", request)).getToken();
        log.info(token);
        log.info(toeknFactory.getInstance().builder(token).decodeToken());
        log.info(toeknFactory.getInstance().builder(token).getClaims());
        try {
            Thread.sleep(1000*4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String reToken = toeknFactory.getInstance().builder(token).reToken();
        log.info(reToken);
        log.info(toeknFactory.getInstance().builder(reToken).decodeToken());
        log.info(toeknFactory.getInstance().builder(reToken).getClaims());
    }

}