package cn.com.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.UsernamePasswordToken;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Log4j
@Data
public class MyToken extends UsernamePasswordToken implements Serializable {
    private String Salt;
    private String UUID;
    private String Phone;

    public MyToken(String username, char[] password) {
        super(username, password);
    }

    public MyToken(String username, String password) {
        super(username, password);
    }

    public MyToken(String username, char[] password, String host) {
        super(username, password, host);
    }

    public MyToken(String username, String password, String host) {
        super(username, password, host);
    }

    @Override
    public Object getPrincipal() {
        if (StringUtils.isBlank(Phone)){
            return getUsername();
        }else {
            return getPhone();
        }
    }

    @Override
    public Object getCredentials() {
        if (StringUtils.isBlank(Phone)){
            return getPassword();
        }else {
            return "phone";
        }
    }

    @Override
    public String toString() {
        return "MyToken{" +
                "Salt='" + Salt + '\'' +
                ", UUID='" + UUID + '\'' +
                ", Phone='" + Phone + '\'' +
                ", host='" + getHost() + '\'' +
                ", rememberMe='" + isRememberMe() + '\'' +
                ", password='" + String.valueOf(getPassword()) + '\'' +
                ", username='" + getUsername() + '\'' +
                '}';
    }
}
