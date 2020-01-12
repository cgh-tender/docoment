package cn.com.filter.token;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;

@Log4j
@SpringBootTest
@RunWith(SpringRunner.class)
class JwtTokenTest {

    private MockHttpServletRequest request;

    @Test
    void main() {
        request = new MockHttpServletRequest();
        request.setCharacterEncoding("UTF-8");
        TokenUserNamePayload admin = new TokenUserNamePayload("admin", request);
        JwtToken jwtToken = new JwtToken();
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJ7XCJJUFwiOlwiMTI3LjAuMC4xXCIsXCJVVUlEXCI6MTIxNjMxNzc5MDkwMjY0NDczNixcImlQXCI6XCIxMjcuMC4wLjFcIixcInNlcmlhbFZlcnNpb25VSURcIjoyNCxcInR5cGVcIjpcIlVTRVJOQU1FXCIsXCJ1VUlEXCI6MTIxNjMxNzc5MDkwMjY0NDczNixcInVzZXJOYW1lXCI6XCJhZG1pblwifSIsIm5iZiI6MTU3ODgyNzczNCwiVG9rZW5QYXlsb2FkQWJzIjoie1wiSVBcIjpcIjEyNy4wLjAuMVwiLFwiVVVJRFwiOjEyMTYzMTc3OTA5MDI2NDQ3MzYsXCJpUFwiOlwiMTI3LjAuMC4xXCIsXCJzZXJpYWxWZXJzaW9uVUlEXCI6MjQsXCJ0eXBlXCI6XCJVU0VSTkFNRVwiLFwidVVJRFwiOjEyMTYzMTc3OTA5MDI2NDQ3MzYsXCJ1c2VyTmFtZVwiOlwiYWRtaW5cIn0iLCJleHAiOjE1Nzg4Mjc3NDQsImlhdCI6MTU3ODgyNzczNCwianRpIjoiMTIxNjMxNzc5MDkwMjY0NDczNiJ9.t4po2hDvtyTKX-47Eh8Wf8dwKZcV8uWA3ZQftEelO7s";
        try {
//            String s = JSONObject.toJSONString(admin);
//            log.info(s);
//            String userNameJwtToken = jwtToken.createUserNameJwtToken(admin);
//            log.info(userNameJwtToken);
//            jwtToken.getTokenPayloadAbs(userNameJwtToken);
            jwtToken.getTokenPayloadAbs(token);
            String tokenUUID = JwtToken.getTokenUUID(token);
            System.out.println(tokenUUID);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}