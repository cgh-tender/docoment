package cn.com.SimpleService.Impl;

import cn.com.SimpleService.AService;
import lombok.Data;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Log4j
@Data
@Service
public class AServiceImpl implements AService {

    @Override
    public String getJdbcType() {
        return Type;
    }

    @Value("Mysql")
    private String Type;

    @Override
    public void create() {
        log.info(Type);
    }
}
