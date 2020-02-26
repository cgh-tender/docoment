package cn.com.filter.shiro;

import cn.com.SpringContextUtil;
import cn.com.filter.shiro.base.MyDefaultWebSubjectFactory;
import cn.com.filter.shiro.base.ShiroRealm;
import cn.com.filter.shiro.filter.*;
import cn.com.filter.shiro.base.MyShiroFilterFactoryBean;
import cn.com.filter.shiro.utils.RedisTokenDao;
import lombok.Data;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@Log4j
@Data
public class ShiroConfig {
    @Value("${data.isSeparation}")
    private String isSeparation;
    /**
     * <p> 凭证匹配器
     * @return HashedCredentialsMatcher
     * @author Haidar
     * @date 2020/1/9 12:08
     **/
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName(SpringContextUtil.hashAlgorithmName.getName());
        hashedCredentialsMatcher.setHashIterations(SpringContextUtil.hashIterations);
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }

    /**
     * <p> 权限认证
     * @return ShiroRealm
     * @author Haidar
     * @date 2020/1/9 12:09
     **/
    @Bean
    public ShiroRealm shiroRealm(){
        ShiroRealm myRealm = new ShiroRealm();
        log.info(">>>>>>>>>>>>>>>ShiroRealm注入加密<<<<<<<<<<<<<");
        String name = SpringContextUtil.hashAlgorithmName.getName();
//        if (name.equals("MY")){
//            myRealm.setCredentialsMatcher(new MyCredentialsMatcher());
//        }else{
//            myRealm.setCredentialsMatcher(hashedCredentialsMatcher());
//        }
        myRealm.setCredentialsMatcher(new MyCredentialsMatcher());
        log.info(">>>>>>>>>>>>>>>ShiroRealm注册完成<<<<<<<<<<<<<");
        return myRealm;
    }

    /**
     * <p> 安全管理器
     * @return DefaultWebSecurityManager
     * @author Haidar
     * @date 2020/1/9 12:09
     **/
    @Bean
    public DefaultWebSecurityManager securityManager(){
        DefaultWebSecurityManager webSecurityManager = new DefaultWebSecurityManager();
        webSecurityManager.setRealm(shiroRealm());
        webSecurityManager.setSubjectFactory(new MyDefaultWebSubjectFactory());
        webSecurityManager.setSessionManager(sessionManager());
        log.info(">>>>>>>>>>>>>>>DefaultWebSecurityManager注册完成<<<<<<<<<<<<<");
        return webSecurityManager;
    }

    @Bean
    public SessionManager sessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(new RedisTokenDao());
        boolean equals = !StringUtils.equals(isSeparation, "2");
        sessionManager.setSessionIdCookieEnabled(equals);
        sessionManager.setSessionIdUrlRewritingEnabled(equals);
        sessionManager.setSessionValidationSchedulerEnabled(equals);
        log.info(">>>>>>>>>>>>>>>SessionManager注册完成 " + equals + " <<<<<<<<<<<<<");
        return sessionManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        MyShiroFilterFactoryBean shiroFilterFactoryBean = new MyShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        //拦截器.
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();
        // 配置不会被拦截的链接 顺序判断，因为前端模板采用了thymeleaf，这里不能直接使用 ("/static/**", "anon")来配置匿名访问，必须配置到每个静态目录
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/html/**", "anon");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/index", "anon");
        filterChainDefinitionMap.put("/frontend/login", "anon");
        filterChainDefinitionMap.put("/hello/**", "anon");
        filterChainDefinitionMap.put("/logout", "logout");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        HashMap<String, Filter> filterHashMap = new HashMap<>();
        filterHashMap.put("logout",new AuthLogoutFilter());
        filterHashMap.put("authc",new MyFormAuthenticationFilter());
        filterHashMap.put("shiroFilter",new ShiroFilter());
        shiroFilterFactoryBean.setFilters(filterHashMap);
        log.info(">>>>>>>>>>>>>>>shiroFilterFactoryBean注册完成<<<<<<<<<<<<<");
        return shiroFilterFactoryBean;
    }

}
