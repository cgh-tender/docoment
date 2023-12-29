package cn.com.cgh.core.exception;

import com.feiniaojin.gracefulresponse.EnableGracefulResponse;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
@EnableGracefulResponse
public class CoreConfig {
    public CoreConfig(){
        System.out.println("init CoreConfig");
    }
}
