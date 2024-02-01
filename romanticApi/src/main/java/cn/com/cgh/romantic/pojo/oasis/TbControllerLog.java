package cn.com.cgh.romantic.pojo.oasis;

import cn.com.cgh.romantic.pojo.TbBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.http.HttpMethod;

/**
 * @author cgh
 */
@EqualsAndHashCode(callSuper = true)
@Schema(description = "记录方法操作")
@Accessors(chain = true)
@TableName("tb_controller_log")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TbControllerLog extends TbBaseEntity {
    @Schema(description = "方法名称")
    private HttpMethod httpMethod;
    @Schema(description = "请求的 URL")
    private String requestUrl;
    @Schema(description = "录请求的客户端 IP 地址")
    private String clientIp;
    @Schema(description = "记录请求体内容，使用 TEXT 类型")
    private String requestBody;
    @Schema(description = "响应状态码")
    private String responseStatusCode;
    @Schema(description = "响应体")
    private String responseBody;
    @Schema(description = "请求的用户代理信息")
    private String userAgent;

}
