package cn.com.cgh.login.controller;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class CustomerBlockHandler {
    public static Logger log = LoggerFactory.getLogger(CustomerBlockHandler.class);

    public static Map handler(String code, BlockException e) {
        log.info("限流了！");
        throw new RuntimeException("被限流了！");
    }
}
