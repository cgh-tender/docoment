package cn.com.cgh.login.auth.controller;

import com.mybatisflex.core.paginate.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import cn.com.cgh.login.auth.entity.Resource;
import cn.com.cgh.login.auth.server.ResourceService;
import org.springframework.web.bind.annotation.RestController;
import java.io.Serializable;
import java.util.List;

/**
 * 商品销售统计 控制层。
 *
 * @author root
 * @since 2024-01-04
 */
@RestController
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    /**
     * 添加商品销售统计。
     *
     * @param resource 商品销售统计
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    public boolean save(@RequestBody Resource resource) {
        return resourceService.save(resource);
    }

    /**
     * 根据主键删除商品销售统计。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable Serializable id) {
        return resourceService.removeById(id);
    }

    /**
     * 根据主键更新商品销售统计。
     *
     * @param resource 商品销售统计
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody Resource resource) {
        return resourceService.updateById(resource);
    }

    /**
     * 查询所有商品销售统计。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public List<Resource> list() {
        return resourceService.list();
    }

    /**
     * 根据商品销售统计主键获取详细信息。
     *
     * @param id 商品销售统计主键
     * @return 商品销售统计详情
     */
    @GetMapping("getInfo/{id}")
    public Resource getInfo(@PathVariable Serializable id) {
        return resourceService.getById(id);
    }

    /**
     * 分页查询商品销售统计。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<Resource> page(Page<Resource> page) {
        return resourceService.page(page);
    }

}
