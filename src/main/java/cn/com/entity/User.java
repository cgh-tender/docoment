package cn.com.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class User {
    private Integer userId;
    private String userName; //登录用户名
    private String password;
    private String salt;//加密密码的盐
    private byte state;//用户状态,0:创建未认证（比如没有激活，没有输入验证码等等）--等待验证的用户 , 1:正常状态,2：用户被锁定.
    private List<UserRole> roleList;// 一个用户具有多个角色
    private LocalDateTime createTime;//创建时间
    private LocalDate expiredDate;//过期日期
    private String email;
}
