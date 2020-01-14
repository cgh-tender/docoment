package cn.com.filter.token;

import io.jsonwebtoken.JwtBuilder;

public class MyUserNameTokenFactory extends JWTFactory {


    @Override
    protected JwtBuilder createTokenBuilder(TokenPayloadAbs abs) {
        return null;
    }

    @Override
    protected TokenPayloadAbs decodeTokenBuilder(String token) {
        return null;
    }
}
