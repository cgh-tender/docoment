package cn.com.support;

import cn.com.entity.User;
import cn.com.SpringContextUtil;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

@Log4j
public class SpringSuperController implements Serializable {

    public HttpServletRequest getRequest(){
       return SpringContextUtil.getRequest();
    }
    public HttpServletResponse getResponse(){
        return SpringContextUtil.getResponse();
    }

    public User getUser(){
        User user = null;
        try {
            HttpServletRequest request = getRequest();
            String token = request.getHeader(SpringContextUtil.TOKEN);
            if (StringUtils.isNoneBlank(token)){

            }else {
                throw new Exception("请携带TOKEN进行登录");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}
