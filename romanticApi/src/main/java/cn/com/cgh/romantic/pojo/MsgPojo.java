package cn.com.cgh.romantic.pojo;

import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author cgh
 */
@Data
@Accessors(chain =true)
@AllArgsConstructor
@NoArgsConstructor
public class MsgPojo<T> {
    private Long id;
    private T msg;
}
