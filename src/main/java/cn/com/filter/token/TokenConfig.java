package cn.com.filter.token;

import cn.com.entity.AuthHashAlgorithmName;
import lombok.Data;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Configuration
@Data
public class TokenConfig<T> implements Serializable {
    private static final long serialVersionUID = 5516075349620653480L;
    @Value("${shiro.token.tokeSubject}")
    private String tokeSubject;
    //token 加密盐
    @Value("${shiro.token.tokenSalt}")
    private String tokenSalt;
    // Token 加密方式
    @Value("${shiro.token.alg}")
    private String alg;
    //Token 刷新时间
    @Value("${shiro.token.tokenExpireMinute}")
    private int tokenExpireMinute;
    //Token pay 类型
    @Value("${shiro.token.tokenPay}")
    // Token 用户是否单点
    private String tokenPay;
    // 用户登录次数
    @Value("${shiro.token.loginNum}")
    private int loginNum;
    // 用户登录超过次数进行锁定时长
    @Value("${shiro.token.loginLockMinute}")
    private int loginLockMinute;
    // 是否锁定用户
    @Value("${shiro.token.userOnline}")
    private Boolean userOnline;
    //系统密码加密盐
    @Value("${system.salt}")
    private String salt;
    //系统超时时常
    @Value("${system.expireMinute}")
    private int expireMinute;
    //加密方式
    @NotNull
    public static final AuthHashAlgorithmName hashAlgorithmName = AuthHashAlgorithmName.MD5;
    //加密次数
    @NotNull
    public static final int hashIterations = 2;

    public String enc(String password, String salt){
        return enc(password,salt,hashIterations);
    }

    public String enc(String password, String salt,int hashIterations){
        SimpleHash result = new SimpleHash(hashAlgorithmName.getName(), password, ByteSource.Util.bytes(salt), hashIterations);
        return result.toString();
    }
}
