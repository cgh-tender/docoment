package cn.com.cgh.romantic.pojo.gateway;

import cn.com.cgh.romantic.pojo.TbBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author cgh
 * 10000
 *
 */
@EqualsAndHashCode(callSuper = true)
@Schema(description = "记录方法操作")
@Accessors(chain = true)
@TableName("tb_cfg_error")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TbCfgError extends TbBaseEntity {
    @Schema(description = "异常码")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long code;
    @Schema(description = "异常码对应提示码表")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long targetCode;
    @Schema(description = "异常信息")
    private String message;
}
