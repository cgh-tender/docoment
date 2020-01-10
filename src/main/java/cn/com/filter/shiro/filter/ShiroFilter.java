package cn.com.filter.shiro.filter;

import cn.com.filter.shiro.base.MyShiroFilterFactoryBean;
import cn.com.SpringContextUtil;
import lombok.extern.log4j.Log4j;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Map;

@Log4j
public class ShiroFilter extends AccessControlFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        log.info("onAccessDenied");
        MyShiroFilterFactoryBean myShiroFilterFactoryBean = SpringContextUtil.getBean(MyShiroFilterFactoryBean.class);
        Map<String, String> chainDefinitionMap = myShiroFilterFactoryBean.getFilterChainDefinitionMap();
        log.info(chainDefinitionMap);
        return true;
    }
}
