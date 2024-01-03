package cn.com.cgh.core.config;

import cn.com.cgh.core.exception.CoreException;
import com.feiniaojin.gracefulresponse.AbstractExceptionAliasRegisterConfig;
import com.feiniaojin.gracefulresponse.EnableGracefulResponse;
import com.feiniaojin.gracefulresponse.ExceptionAliasRegister;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@ConditionalOnClass(value = {AbstractExceptionAliasRegisterConfig.class})
@EnableGracefulResponse
@Slf4j
public class GracefulResponseConfig extends AbstractExceptionAliasRegisterConfig {
    static {
        log.info("GracefulResponseConfig init");
    }
    private final List<CoreException> list;

    public GracefulResponseConfig(@Autowired List<CoreException> list) {
        this.list = list;
    }

    @Override
    protected void registerAlias(ExceptionAliasRegister register) {
        list.forEach(e -> register.doRegisterExceptionAlias(e.getClass()));
    }
}
