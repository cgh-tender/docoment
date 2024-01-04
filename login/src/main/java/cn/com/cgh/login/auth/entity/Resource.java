package cn.com.cgh.login.auth.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品销售统计 实体类。
 *
 * @author root
 * @since 2024-01-04
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "sys_resource")
public class Resource implements Serializable {

    /**
     * 主键
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 菜单code
     */
    private Long code;

    /**
     * 父菜单code
     */
    private Long parentCode;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * desc
     */
    @Column(value = "displayName")
    private String displayName;

    /**
     * 创建人
     */
    private Long createUser;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 最终修改人
     */
    private Long modifyUser;

    /**
     * 修改时间
     */
    private LocalDateTime modifyTime;

    /**
     * 是否删除 1: 是，2：否
     */
    private Integer isDelete;

}
