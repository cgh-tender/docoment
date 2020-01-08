package cn.com.filter.shiro;

import cn.com.entity.User;
import cn.com.entity.UserRole;
import cn.com.support.Constants;
import lombok.extern.log4j.Log4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Log4j
public class ShiroRealm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
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

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userName = (String) token.getPrincipal();
        User user = new User();
        return new SimpleAuthenticationInfo(
                user, //这里传入的是user对象，比对的是用户名，直接传入用户名也没错，但是在授权部分就需要自己重新从数据库里取权限
                user.getPassword(), //密码
                ByteSource.Util.bytes(Constants.SALT),//salt=username+salt
                getName()  //realm name
        );
    }
}
