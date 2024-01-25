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
        TbUserRole admin = TbUserRole
                .builder()
                .roleId(1L)
                .userId(1L).build();

        TbUserRole test = TbUserRole
                .builder()
                .roleId(2L)
                .userId(2L).build();
        tbCfgUserRoleService.save(admin);
        tbCfgUserRoleService.save(test);
    }
}
