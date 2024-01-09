package cn.com.cgh.login.controller;

import cn.com.cgh.login.pojo.User;
import cn.com.cgh.romantic.login.ILoginController;
import cn.com.cgh.romantic.login.IMenuController;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@Slf4j
public class LoginController implements ILoginController {
    @Autowired
    private IMenuController controller;
    /**
     * 获取 验证码
     * @return
     */
    @Override
    @GetMapping("/getCode")
//    @Cacheable(cacheNames = "login", cacheManager = Constants.REDIS_CACHE_MANAGER_NAME, key = "#code")
    @SentinelResource(value = cn.com.cgh.romantic.config.Constants.ONE_RULE
            ,blockHandler = "handler",blockHandlerClass = CustomerBlockHandler.class
            ,fallback = "getCodeFallback"
    )
    public Map getCode(String code){
        Map<String,String> map = new HashMap();
        log.info("login");
        map.put("url","http://dummyimage.com/100x40/dcdfe6/000000.png&text="+code);
        List list = controller.queryMenu();
        call();
        log.info(JSONObject.valueToString(list));
        return map;
    }

    @Retryable(retryFor = {RuntimeException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000, multiplier = 1.5))
    public String call(){
        log.info("调用成功");
        throw new RuntimeException("调用失败");
    }

    public Map getCodeFallback(){
        log.info("获取验证码失败");
        throw new RuntimeException("获取验证码失败");
    }


    /**
     * 登录
     * @param user
     * @return
     */
    @PostMapping("/login")
    public Map login(@RequestBody User user){
        Map<String, String> map = new HashMap<>();
        map.put("token",user.getUsername());
        return map;    }

    /**
     * user info
     * @return
     */
    @GetMapping("/info")
    public Map info(HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        Map<String, Object> map = new HashMap<>();
        map.put("roles", authorization.contains("admin") ? Arrays.asList("admin","editor") : List.of("editor"));
        return map;
    }

}
