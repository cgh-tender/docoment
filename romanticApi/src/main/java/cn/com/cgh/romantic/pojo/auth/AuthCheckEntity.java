package cn.com.cgh.romantic.pojo.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;

/**
 * @author cgh
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthCheckEntity {
    private String url;
    private String httpMethod;
}
