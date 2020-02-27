package cn.com.filter.token.Impl;

import cn.com.filter.token.Body.Impl.TokenUserNamePayload;
import cn.com.utils.Redis.RedisUtil;
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
    @Resource
    private RedisUtil redisUtil;
    @Before
    public void setUp(){
        request = new MockHttpServletRequest();
        request.setCharacterEncoding("UTF-8");
//        response = new MockHttpServletResponse();
    }

    @Test
    public void main() {
        String token = toeknFactory.getInstance().builder(new TokenUserNamePayload("admin","1" ,request)).getToken();
        log.info(token);
        log.info(toeknFactory.getInstance().builder(token).decodeToken());
        log.info(toeknFactory.getInstance().builder(token).getClaims());
        log.info(toeknFactory.getInstance().builder(token).saveToken());
        try {
            Thread.sleep(1000*10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info(redisUtil.getExpire("admin"));
        log.info(redisUtil.getExpire(token + "_keyPair"));
        log.info(toeknFactory.getInstance().builder(token).isOverdue());
        String reToken = toeknFactory.getInstance().builder(token).reToken();
        log.info(reToken);
        log.info(toeknFactory.getInstance().builder(reToken).decodeToken());
        log.info(toeknFactory.getInstance().builder(reToken).getClaims());
    }

}