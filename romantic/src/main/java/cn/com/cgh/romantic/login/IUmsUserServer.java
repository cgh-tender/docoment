package cn.com.cgh.romantic.login;

import cn.com.cgh.romantic.pojo.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "login"
        , configuration = IUmsUserServerConfiguration.class
        , contextId = "menu-0"
)
public interface IUmsUserServer {
    @GetMapping("/loadByUsername/${userName}")
    UserDto loadUserByUsername(@RequestParam String userName);
}

class IUmsUserServerConfiguration{
    @Bean
    public IUmsUserClientFallback iUmsUserClient(){
        return new IUmsUserClientFallback();
    }
}
class IUmsUserClientFallback implements IUmsUserServer{
    @Override
    public UserDto loadUserByUsername(String userName) {
        return null;
    }
}