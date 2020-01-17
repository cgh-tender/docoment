package cn.com.SimpleService;

import cn.com.SimpleService.AService;
import cn.com.SimpleService.AServiceSFactory;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Log4j
public class AServiceFactory implements AServiceSFactory {

    private Map<String, AService> data = new ConcurrentHashMap<>();

    public AServiceFactory(List<AService> services){
        services.forEach(c -> {
            log.info(c);
            log.info("2222222222222222222");
            data.put(c.getJdbcType(),c);
        });

    }
    @Override
    public AService getInstance(String type) {
        return data.get(type);
    }
}
