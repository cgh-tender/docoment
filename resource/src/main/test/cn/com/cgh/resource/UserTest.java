package cn.com.cgh.resource;

import cn.com.cgh.resource.auth.service.impl.TbCfgUserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@Slf4j
public class UserTest {
    @Autowired
    private TbCfgUserServiceImpl tbCfgUserService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void test() {
//        TbCfgUser admin = TbCfgUser.builder()
//                .username("admin")
//                .phone("18334774205")
//                .email("late_tender@163.com")
//                .password(passwordEncoder.encode("12345678"))
//                .status(UserStatus.NORMAL)
//                .gender(GenderStatus.MALE)
//                .build();
//        TbCfgUser test = TbCfgUser.builder()
//                .username("test")
//                .phone("18334774205")
//                .email("late_tender@163.com")
//                .password(passwordEncoder.encode("12345678"))
//                .status(UserStatus.NORMAL)
//                .gender(GenderStatus.MALE)
//                .build();
//        tbCfgUserService.save(admin);
//        tbCfgUserService.save(test);
    }
}
