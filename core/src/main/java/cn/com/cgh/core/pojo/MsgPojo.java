package cn.com.cgh.core.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author cgh
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MsgPojo {
    private Long id;
    private Object msg;
}
