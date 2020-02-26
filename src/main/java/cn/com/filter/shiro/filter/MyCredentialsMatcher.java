package cn.com.filter.shiro.filter;

import cn.com.SpringContextUtil;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

@Log4j
public class MyCredentialsMatcher extends HashedCredentialsMatcher {

    public MyCredentialsMatcher() {
        super();
        log.info(">>>>>>>>>>>>>>>MyCredentialsMatcher注册完成<<<<<<<<<<<<<");
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) throws AuthenticationException {
        log.info(">>>>>>>>>>>>>>>验证密码对比<<<<<<<<<<<<<");
//        if (StringUtils.equals(SpringContextUtil.hashAlgorithmName.getName(),"MD5")){
//            HashedCredentialsMatcher hashedCredentialsMatcher = SpringContextUtil.getBean(HashedCredentialsMatcher.class);
//            if (hashedCredentialsMatcher.doCredentialsMatch(token,info)){
//                log.info(">>>>>>>>>>>>>>>验证密码对比成功<<<<<<<<<<<<<");
//                return true;
//            }else {
//                log.info(">>>>>>>>>>>>>>>验证密码对比失败<<<<<<<<<<<<<");
//                return false;
//            }
//        }
        try {
            boolean match = super.doCredentialsMatch(token, info);
            if (match){
                return true;
            }else {
                throw new IncorrectCredentialsException();
            }
        }catch (AuthenticationException e){
            throw new AuthenticationException(e.getMessage());
        }
    }
}
