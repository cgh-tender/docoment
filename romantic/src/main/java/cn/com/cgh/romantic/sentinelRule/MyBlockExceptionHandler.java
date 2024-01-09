package cn.com.cgh.romantic.sentinelRule;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.feiniaojin.gracefulresponse.GracefulResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class MyBlockExceptionHandler implements BlockExceptionHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws Exception {
        if(e instanceof FlowException)
            GracefulResponse.raiseException("1001","接口限流了",e);
        else if(e instanceof DegradeException)
            GracefulResponse.raiseException("1002","服务降级了",e);
        else if(e instanceof ParamFlowException)
            GracefulResponse.raiseException("1003","参数限流了",e);
        else if(e instanceof AuthorityException)
            GracefulResponse.raiseException("1004", "权限规则不通过", e);
        else if(e instanceof SystemBlockException) GracefulResponse.raiseException("1005", "系统保护", e);
    }
}
