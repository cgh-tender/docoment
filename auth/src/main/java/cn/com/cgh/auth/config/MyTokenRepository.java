package cn.com.cgh.auth.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class MyTokenRepository implements PersistentTokenRepository {
    @Autowired
    private RedisTemplate<String, Object> redisTemplateSO;
    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        String tokenValue = token.getTokenValue();
        String username = token.getUsername();
        String series = token.getSeries();
        Date date = token.getDate();
        log.info("", tokenValue);
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {

    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        return null;
    }

    @Override
    public void removeUserTokens(String username) {

    }
}
