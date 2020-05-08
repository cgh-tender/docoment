package cn.com.filter.shiro.filter;

import cn.com.SpringContextUtil;
import cn.com.filter.token.Body.Impl.TokenUserNamePayload;
import cn.com.filter.token.Body.Impl.TokenUserPhonePayload;
import cn.com.filter.token.TokenConfig;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

@Log4j
public class MyCredentialsMatcher extends HashedCredentialsMatcher {
    private TokenConfig tokenCfg = null;

    public MyCredentialsMatcher() {
        super();
        log.info(">>>>>>>>>>>>>>>MyCredentialsMatcher注册完成<<<<<<<<<<<<<");
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) throws AuthenticationException {
        log.info(">>>>>>>>>>>>>>>验证密码对比<<<<<<<<<<<<<");
        if (tokenCfg == null) tokenCfg = SpringContextUtil.getBean(TokenConfig.class);
        try {
            String password;
            if (tokenCfg.getTokenPay().equals("userName")){
                TokenUserNamePayload token1 = (TokenUserNamePayload) token;
                password = token1.getPassWord();
            }else {
                TokenUserPhonePayload token1 = (TokenUserPhonePayload) token;
                password = token1.getPassWord();
            }
            String credentials = info.getCredentials().toString();
            if (StringUtils.equals(credentials, tokenCfg.enc(password, tokenCfg.getTokenSalt()))){
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
