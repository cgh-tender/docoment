package cn.com.cgh.resource.auth.service;

import cn.com.cgh.romantic.pojo.resource.TbCfgTableResource;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 * 表资源 服务类
 * </p>
 *
 * @author cgh
 * @since 2024-01-24
 */
@RequestMapping("/tables")
public interface ITbCfgTableResourceService extends IService<TbCfgTableResource> {
    @PostMapping()
    public boolean saves(TbCfgTableResource tableResource);
}
