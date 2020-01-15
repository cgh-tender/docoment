package cn.com.filter.token;

import cn.com.filter.token.Body.TokenPayloadAbs;
import cn.com.filter.token.Body.TokenUserNamePayload;
import cn.com.filter.token.Key.MySigningKey;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j
@RunWith(SpringRunner.class)
public class JWTUtilsTest {
    private MockHttpServletRequest request;

    @Test
    public void test(){
        request = new MockHttpServletRequest();
        request.setCharacterEncoding("UTF-8");
        TokenUserNamePayload admin = new TokenUserNamePayload("admin", request);
        log.info(JSONObject.parseObject(JSONObject.toJSONString(admin),Map.class));
        JWTUtils jwtUtils = new JWTUtils(admin,new MySigningKey());
        String token = jwtUtils.builder();
        log.info(token);
        log.info(jwtUtils.getPublicKey(JWTUtils.toeknSalt));
        TokenPayloadAbs payload = jwtUtils.getPayload(token);
        log.info(payload);
    }
}