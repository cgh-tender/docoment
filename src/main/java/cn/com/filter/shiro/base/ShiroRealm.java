package cn.com.filter.shiro.base;

import cn.com.entity.User;
import cn.com.entity.UserRole;
import cn.com.SpringContextUtil;
import lombok.extern.log4j.Log4j;
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

/***
 * <p> TODO
 * @author Haidar
 * @date 2020/1/9 11:20
 *
 *
 *                                                    __----~~~~~~~~~~~------___
 *                                   .  .   ~~//====......          __--~ ~~
 *                   -.            \_|//     |||\\  ~~~~~~::::... /~
 *                ___-==_       _-~o~  \/    |||  \\            _/~~-
 *        __---~~~.==~||\=_    -_--~/_-~|-   |\\   \\        _/~
 *    _-~~     .=~    |  \\-_    '-~7  /-   /  ||    \      /
 *  .~       .~       |   \\ -_    /  /-   /   ||      \   /
 * /  ____  /         |     \\ ~-_/  /|- _/   .||       \ /
 * |~~    ~~|--~~~~--_ \     ~==-/   | \~--===~~        .\
 *          '         ~-|      /|    |-~\~~       __--~~
 *                      |-~~-_/ |    |   ~\_   _-~            /\
 *                           /  \     \__   \/~                \__
 *                       _--~ _/ | .-~~____--~-/                  ~~==.
 *                      ((->/~   '.|||' -_|    ~~-/ ,              . _||
 *                                 -_     ~\      ~~---l__i__i__i--~~_/
 *                                 _-~-__   ~)  \--______________--~~
 *                               //.-~~~-~_--~- |-------~~~~~~~~
 *                                      //.-~~~--\
 */
@Log4j
public class ShiroRealm extends AuthorizingRealm {
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
        user.setPassword("1");
        return new SimpleAuthenticationInfo(
                user, //这里传入的是user对象，比对的是用户名，直接传入用户名也没错，但是在授权部分就需要自己重新从数据库里取权限
                user.getPassword(), //密码
                ByteSource.Util.bytes(SpringContextUtil.SALT),//salt=username+salt
                getName()  //realm name
        );
    }

    @Override
    protected void assertCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) throws AuthenticationException {
        log.info("密码匹配");
        super.assertCredentialsMatch(token, info);
    }
}
