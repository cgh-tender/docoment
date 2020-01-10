package cn.com;

import cn.com.entity.AuthHashAlgorithmName;
import cn.com.entity.Result;
import cn.com.utils.AuthFilterItemProperties;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

@Component
@Log4j
public class SpringContextUtil implements ApplicationContextAware {

    public static AuthFilterItemProperties authFilterItemProperties;

    public static final String TOKEN = "TOKEN";
    public static final String SALT = "chenguohai";
    @NonNull
    public static final AuthHashAlgorithmName hashAlgorithmName = AuthHashAlgorithmName.MY;
    @NonNull
    public static final int hashIterations = 2;

    @Getter
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext args0) throws BeansException {
        SpringContextUtil.applicationContext = args0;
    }

    public static Object getBean(@NonNull String name){
        return applicationContext.getBean(name);
    }

    //通过类型获取上下文中的bean
    public static Object getBean(@NonNull Class<?> requiredType){
        return applicationContext.getBean(requiredType);
    }

    public static HttpServletRequest getRequest(){
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    public static HttpServletResponse getResponse(){
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
    }

    public static boolean isAjax(HttpServletRequest req) {
        String contentTypeHeader = req.getHeader("content-type");
        String acceptHeader = req.getHeader("accept");
        String xRequestedWith = req.getHeader("x-requested-wit");
        return (contentTypeHeader != null && contentTypeHeader.contains("application/json"))
                || (acceptHeader != null && acceptHeader.contains("application/json"))
                || "XMLHttpRequest".equalsIgnoreCase(xRequestedWith);
    }

    public static boolean isAjax() {
        HttpServletRequest req = getRequest();
        String contentTypeHeader = req.getHeader("content-type");
        String acceptHeader = req.getHeader("accept");
        String xRequestedWith = req.getHeader("x-requested-wit");
        return (contentTypeHeader != null && contentTypeHeader.contains("application/json"))
                || (acceptHeader != null && acceptHeader.contains("application/json"))
                || "XMLHttpRequest".equalsIgnoreCase(xRequestedWith);
    }

    public static String md5(String password){
        return md5(password,SALT);
    }

    public static String md5(String password, String salt){
        return md5(password,salt,hashIterations);
    }

    public static String md5(String password, String salt,int hashIterations){

        SimpleHash result = new SimpleHash(hashAlgorithmName.getName(), password, ByteSource.Util.bytes(salt), hashIterations);

        return result.toString();
    }

    public static void write(HttpServletResponse response,String message,int code) throws IOException {
        String success = Result.success(code, message);
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(success);
        writer.flush();
        writer.close();
    }

    /**
     * <p> 返回是否创建 TOKEN
     * @return boolean
     * @author Haidar
     * @date 2020/1/10 15:16
     **/
    public static boolean isSeparation(HttpServletRequest request) throws Exception{
        if (authFilterItemProperties == null){
            authFilterItemProperties = (AuthFilterItemProperties) SpringContextUtil.getBean(AuthFilterItemProperties.class);
        }
        int separation = authFilterItemProperties.getIsSeparation();
        if (separation == 0){
            String token = request.getHeader("TOKEN");
            if (StringUtils.isNotBlank(token)) {
                return Boolean.FALSE;
            } else {
                return Boolean.TRUE;
            }
        }else if(separation == 1){
            return Boolean.TRUE;
        }else if (separation == 2){
            return Boolean.FALSE;
        }
        throw new Exception("请配置 isSeparation 属性");
    }
}
