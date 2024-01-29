package cn.com.cgh.romantic.pojo;

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
public class MsgPojo<T> {
    private Long id;
    private T msg;
}
