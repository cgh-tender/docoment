package cn.com.cgh.romantic.pojo.resource;

import cn.com.cgh.romantic.pojo.TbBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author cgh
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "表资源")
@TableName("tb_cfg_table_resource")
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TbCfgTableResource extends TbBaseEntity {
    @Schema(description = "表名")
    private String tableEn;
    @Schema(description = "表名")
    private String tableCn;
    @Schema(description = "字段")
    private String filedEn;
    @Schema(description = "字段名称")
    private String filedCn;
    @Schema(description = "字段类型")
    private String filedType;
    @Schema(description = "字段长度")
    private Integer filedLength;
    @Schema(description = "字段描述")
    private String filedDesc;
    @Schema(description = "字段是否必填")
    private Integer filedRequired;
    @Schema(description = "字段是否唯一")
    private Integer filedUnique;
    @Schema(description = "字段是否可为空")
    private Integer filedNullable;
    @Schema(description = "字段是否为主键")
    private Integer filedPk;
    @Schema(description = "字段是否为自增")
    private Integer filedAutoincrement;
    @Schema(description = "字段是否为索引")
    private Integer filedIndex;
    @Schema(description = "字段是否为外键")
    private Integer filedForeignKey;
}
