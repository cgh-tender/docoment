package cn.com.cgh.login;

import cn.com.cgh.login.auth.service.impl.TbCfgOrganizationImpl;
import cn.com.cgh.romantic.pojo.TbCfgOrganization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@Slf4j
public class LoginApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(LoginApplication.class, args);
        TbCfgOrganizationImpl organization = applicationContext.getBean(TbCfgOrganizationImpl.class);
        TbCfgOrganization cfgOrganization = new TbCfgOrganization();
        cfgOrganization.setName("test");
        cfgOrganization.setDescription("test");
        cfgOrganization.setParentId(0L);
        organization.save(cfgOrganization);
    }
}
