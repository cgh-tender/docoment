package cn.com.filter.shiro.filter;

import cn.com.SpringContextUtil;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

@Log4j
public class MyCredentialsMatcher extends HashedCredentialsMatcher {

    public MyCredentialsMatcher() {
        super();
        log.info(">>>>>>>>>>>>>>>MyCredentialsMatcher注册完成<<<<<<<<<<<<<");
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        log.info(">>>>>>>>>>>>>>>验证密码对比<<<<<<<<<<<<<");
        if (StringUtils.equals(SpringContextUtil.hashAlgorithmName.getName(),"MD5")){
            HashedCredentialsMatcher hashedCredentialsMatcher = (HashedCredentialsMatcher) SpringContextUtil.getBean(HashedCredentialsMatcher.class);
            if (hashedCredentialsMatcher.doCredentialsMatch(token,info)){
                log.info(">>>>>>>>>>>>>>>验证密码对比成功<<<<<<<<<<<<<");
                return true;
            }else {
                log.info(">>>>>>>>>>>>>>>验证密码对比失败<<<<<<<<<<<<<");
                return false;
            }
        }
        return super.doCredentialsMatch(token, info);
    }
}
