package cn.com.cgh.romantic.login;

import cn.com.cgh.gallery.util.ResponseImpl;
import cn.com.cgh.romantic.pojo.resource.TbCfgUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "resource"
        , configuration = IUmsUserServerConfiguration.class
        , contextId = "menu-0"
)
public interface IUmsUserServer {
    @GetMapping("/user/loadByUsername/{username}")
    ResponseImpl<TbCfgUser> loadUserByUsername(@PathVariable String username);
}

class IUmsUserServerConfiguration{
    @Bean
    public IUmsUserClientFallback iUmsUserClient(){
        return new IUmsUserClientFallback();
    }
}
class IUmsUserClientFallback implements IUmsUserServer{

    @Override
    public ResponseImpl<TbCfgUser> loadUserByUsername(String userName) {
        throw new RuntimeException("用户异常");
    }
}