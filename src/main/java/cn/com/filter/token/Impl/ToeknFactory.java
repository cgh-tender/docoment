package cn.com.filter.token.Impl;

import cn.com.SpringContextUtil;
import cn.com.filter.token.TokenBuilder;
import cn.com.filter.token.TokenServiceFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ToeknFactory implements TokenServiceFactory {
    public static SpringContextUtil springContextUtil = SpringContextUtil.getBean(SpringContextUtil.class);

    private Map<String, TokenBuilder> data = new ConcurrentHashMap<>();

    public ToeknFactory(List<TokenBuilder> services){
        services.forEach(c-> {
            data.put(c.getType(),c);
        });
    }

    @Override
    public TokenBuilder getInstance() {
        return data.get(springContextUtil.getAlg());
    }
}
