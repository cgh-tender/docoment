package cn.com.filter.shiro.filter;

import lombok.extern.log4j.Log4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

@Log4j
public class MyCredentialsMatcher extends SimpleCredentialsMatcher {
    public MyCredentialsMatcher() {
        super();
        log.info(">>>>>>>>>>>>>>>MyCredentialsMatcher注册完成<<<<<<<<<<<<<");
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        log.info(">>>>>>>>>>>>>>>验证密码对比<<<<<<<<<<<<<");
        return super.doCredentialsMatch(token, info);
    }
}
