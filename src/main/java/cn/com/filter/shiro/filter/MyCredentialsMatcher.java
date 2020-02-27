package cn.com.filter.shiro.filter;

import cn.com.SpringContextUtil;
import cn.com.filter.token.Body.Impl.TokenUserNamePayload;
import cn.com.filter.token.Body.Impl.TokenUserPhonePayload;
import cn.com.filter.token.Body.TokenPayloadAbs;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.subject.PrincipalCollection;

@Log4j
public class MyCredentialsMatcher extends HashedCredentialsMatcher {
    private SpringContextUtil springContextUtil = null;

    public MyCredentialsMatcher() {
        super();
        log.info(">>>>>>>>>>>>>>>MyCredentialsMatcher注册完成<<<<<<<<<<<<<");
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) throws AuthenticationException {
        log.info(">>>>>>>>>>>>>>>验证密码对比<<<<<<<<<<<<<");
        if (springContextUtil == null)springContextUtil = SpringContextUtil.getBean(SpringContextUtil.class);
        try {
            String password;
            if (springContextUtil.getTokenPay().equals("userName")){
                TokenUserNamePayload token1 = (TokenUserNamePayload) token;
                password = token1.getPassWord();
            }else {
                TokenUserPhonePayload token1 = (TokenUserPhonePayload) token;
                password = token1.getPassWord();
            }
            String credentials = info.getCredentials().toString();
            if (StringUtils.equals(credentials,springContextUtil.enc(password, springContextUtil.getTokenSalt()))){
                return true;
            }

            Object tokenHashedCredentials =
                    hashProvidedCredentials(token, info);
            Object accountCredentials = getCredentials(info);

            return equals(tokenHashedCredentials, accountCredentials);
        }catch (AuthenticationException e){
            throw new AuthenticationException(e.getMessage());
        }
    }
}
