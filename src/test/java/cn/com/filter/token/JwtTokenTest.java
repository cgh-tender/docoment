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
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJUb2tlblBheWxvYWRBYnMiOiJ7XCJJUFwiOlwiMTI3LjAuMC4xXCIsXCJVVUlEXCI6MTIxNjYwMjM3OTI5MDQ4ODgzMixcImFsZ1wiOlwiUGFpcktleVwiLFwiaVBcIjpcIjEyNy4wLjAuMVwiLFwic2VyaWFsVmVyc2lvblVJRFwiOjI0LFwidHlwZVwiOlwiVVNFUk5BTUVcIixcInVVSURcIjoxMjE2NjAyMzc5MjkwNDg4ODMyLFwidXNlck5hbWVcIjpcImFkbWluXCJ9Iiwic3ViIjoiVE9LRU4iLCJqdGkiOiIxMjE2NjAyMzc5MjkwNDg4ODMyIiwiaWF0IjoxNTc4ODk1NTg1LCJleHAiOjE1Nzg4OTU2NDV9.Fqi56Uqein3yNtgw4Gt_j7INftuu5htQVNkXKDdwu263zLVqOrVxivfkJVLctsCZCk6ifChlOgtSdT8d7aAyujIeEBsyRHDNVUQEpLWEhueFqUD7JLWKRgQC2oExBKJyne4MC4Jk2L9aGNK_UdAJ38qu_4pUgwivQSrKMBR8auKhFDgNhiudDOa9pKBtyCY26DduDDU6XCCK0gpsL2SF_-WBAUZ6QO5GiXS19tFmLzBtiw6V4MZy7Rhq8ZG4wOQ-FgTZsjwuyR-3topxw94tOv7PNBFfIponZ9RzF0yTLCfjFMtUhHDtlh0jAQCuYHAbEPvMrq7s0U8hW3seR-dWUQ";
        try {
            String s = JSONObject.toJSONString(admin);
            String userNameJwtToken = jwtToken.createJwtToken(admin);
            log.info(userNameJwtToken);
            jwtToken.getTokenPayloadAbs(userNameJwtToken);
//            System.out.println(jwtToken.getTokenPayloadAbs(token));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}