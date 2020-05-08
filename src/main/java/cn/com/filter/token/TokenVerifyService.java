package cn.com.filter.token;

import cn.com.filter.token.Body.TokenPayloadAbs;
import io.jsonwebtoken.Claims;

public interface TokenVerifyService {
    /**
     * Token是否过期 是: true , 否 false
     */
    Boolean isOverdue();
    /**
     * Token是否过期 是: true , 否 false
     */
    Boolean SysIsOverdue();
    /**
     * 重新加载出一个token
     * @return
     */
    String reToken();

    /**
     * 保存Token
     * @return true 成功
     */
    Boolean saveToken();

    /**
     * 修改存入时间
     * @return true 成功
     */
    Boolean upTokenTime();

    /**
     * 清空储存
     */
    void clear();

    /**
     * 剩余登录次数
     * 当为0时 登录次数用完进行锁定用户
     * 当为-1时 登录不限制
     */
    int remainLoginNum();

    /**
     * 锁定剩余时长
     * 当为 -1时 登录时长不限制
     * 返回数值为 (秒)
     */
    long remainLoginTime();

    /**
     * 获取 Toekn 内容
     */
    Claims getClaims();
    /**
     * 解析 Toekn 实例
     **/

    TokenPayloadAbs decodeToken();
}
