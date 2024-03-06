package cn.com.cgh.resource.auth.service;

import cn.com.cgh.romantic.pojo.resource.TbCfgPosition;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * <p>
 * 职位表 服务类
 * </p>
 *
 * @author cgh
 * @since 2024-01-24
 */
public interface ITbCfgPositionService extends IService<TbCfgPosition> {
    @GetMapping("/getTest1")
    public String getTest();
}
