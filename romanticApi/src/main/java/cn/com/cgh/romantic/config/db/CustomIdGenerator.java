package cn.com.cgh.romantic.config.db;

import cn.com.cgh.romantic.util.IdWork;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author cgh
 */
@AllArgsConstructor
@Slf4j
public class CustomIdGenerator implements IdentifierGenerator {
    static {
        log.info("CustomIdGenerator:已启动");
    }
    private final IdWork idWork;
    @Override
    public boolean assignId(Object idValue) {
        return IdentifierGenerator.super.assignId(idValue);
    }

    @Override
    public Number nextId(Object entity) {
        return idWork.nextId();
    }

    @Override
    public String nextUUID(Object entity) {
        return IdentifierGenerator.super.nextUUID(entity);
    }
}
