package cn.com.cgh.romantic.util;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

/**
 * @author cgh
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseImpl<T> {
    private String code;
    private String message;
    @Builder.Default
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime timestamp = LocalDateTime.now();
    private T data;

    public ResponseImpl<T> success(){
        if (StringUtils.isBlank(this.code)){
            this.code = "0";
        }
        if (StringUtils.isBlank(this.message)){
            this.message = "成功";
        }
        return this;
    }
    public ResponseImpl<T> full() {
        if (StringUtils.isBlank(this.code)) {
            this.code = "1";
        }
        if (StringUtils.isBlank(this.message)) {
            this.message = "失败";
        }
        return this;
    }

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }
}
