package cn.com.cgh.core.exception.config;

import cn.com.cgh.core.exception.CoreException;
import com.feiniaojin.gracefulresponse.AbstractExceptionAliasRegisterConfig;
import com.feiniaojin.gracefulresponse.ExceptionAliasRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class GracefulResponseConfig extends AbstractExceptionAliasRegisterConfig {
    private final List<CoreException> list;

    public GracefulResponseConfig(@Autowired List<CoreException> list) {
        this.list = list;
    }

    @Override
    protected void registerAlias(ExceptionAliasRegister register) {
        list.forEach(e -> register.doRegisterExceptionAlias(e.getClass()));
    }
}
