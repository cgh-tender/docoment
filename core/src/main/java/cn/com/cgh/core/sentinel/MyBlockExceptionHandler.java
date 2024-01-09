package cn.com.cgh.core.sentinel;

import cn.com.cgh.core.util.ResponseImpl;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class MyBlockExceptionHandler implements BlockExceptionHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws Exception {
        if(e instanceof FlowException)
            ResponseImpl.builder().code("1001").msg("接口限流了");
        else if(e instanceof DegradeException)
            ResponseImpl.builder().code("1002").msg("服务降级了");
        else if(e instanceof ParamFlowException)
            ResponseImpl.builder().code("1003").msg("参数限流了");
        else if(e instanceof AuthorityException)
            ResponseImpl.builder().code("1004").msg("权限规则不通过");
        else if(e instanceof SystemBlockException)
            ResponseImpl.builder().code("1005").msg("系统保护");
    }
}
