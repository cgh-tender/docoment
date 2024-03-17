package cn.com.cgh.resource;

import cn.com.cgh.resource.auth.service.ITbCfgRoleService;
import cn.com.cgh.resource.auth.service.ITbUserRoleService;
import cn.com.cgh.romantic.pojo.resource.TbUserRole;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class RoleTest {
    @Autowired
    private ITbCfgRoleService tbCfgRoleService;
    @Autowired
    private ITbUserRoleService tbCfgUserRoleService;

    @Test
    public void test() {
        TbUserRole admin = new TbUserRole()
                .setRoleId(1L)
                .setUserId(1L);

        TbUserRole test = new TbUserRole()
                .setRoleId(2L)
                .setUserId(2L);
        tbCfgUserRoleService.save(admin);
        tbCfgUserRoleService.save(test);
    }
}
