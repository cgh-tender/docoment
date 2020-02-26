package cn.com.filter.shiro.base;

import cn.com.entity.User;
import cn.com.entity.UserRole;
import cn.com.SpringContextUtil;
import lombok.extern.log4j.Log4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Log4j
public class ShiroRealm extends AuthorizingRealm {
    @Resource
    private SpringContextUtil springContextUtil;
    /**
     * <p> 为当前登录成功的用户授予权限和分配角色
     * @return AuthorizationInfo
     * @author Haidar
     * @date 2020/1/9 11:19
     **/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info(SpringContextUtil.getRequest().getRequestURI());
        log.info("授权");
        Object principal = principalCollection.getPrimaryPrincipal();
        Set<String> roles = new HashSet<String>();
        User user = new User();
        List<UserRole> roleList = user.getRoleList();
        for (UserRole ur: roleList) {
            roles.add(String.valueOf(ur.getRoleId()));
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
        return info;
    }

    /**
     * <p> 验证当前登录的用户，获取认证信息
     * @return AuthenticationInfo
     * @author Haidar
     * @date 2020/1/9 11:19
     **/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.info("登录验证");
        String userName = (String) token.getPrincipal();
        User user = new User();
        user.setUserName("admin");
        user.setPassword("bfa481bf1e4d1f604ea9b86fefd4e507");
//        user.setPassword("14acc5c44989f7b5e5d37f2c3721f535");
        return new SimpleAuthenticationInfo(
                user, //这里传入的是user对象，比对的是用户名，直接传入用户名也没错，但是在授权部分就需要自己重新从数据库里取权限
                user.getPassword(), //密码
                ByteSource.Util.bytes(springContextUtil.getAlg()),//salt=username+salt
                getName()  //realm name
        );
    }

    @Override
    protected void assertCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) throws AuthenticationException {
        log.info("密码匹配");
        super.assertCredentialsMatch(token, info);
    }
}
