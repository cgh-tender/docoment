package cn.com.cgh.auth.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description = "Oauth2TokenDto")
public class Oauth2TokenDto {
    @Schema(description = "访问令牌")
    private String token;
    @Schema(description = "刷令牌")
    private String refreshToken;
    @Schema(description = "访问令牌头前缀")
    private String tokenHead;
    @Schema(description = "有效时间（秒）")
    private int expiresIn;
}
