package cn.com.cgh.romantic.util;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author cgh
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ResponseImpl<T> {
    private String code;
    private String message;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime timestamp = LocalDateTime.now();
    private T data;

    private List<T> records;
    private Long total;

    public static <T> ResponseImpl<T> ok(T data) {
        return new ResponseImpl<T>().setData(data).success();
    }

    public static <T> ResponseImpl<T> ok() {
        return new ResponseImpl<T>().success();
    }

    public static <T> ResponseImpl<T> full(String message) {
        return new ResponseImpl<T>().setMessage(message).error();
    }

    private ResponseImpl<T> success() {
        if (StringUtils.isBlank(this.code)) {
            this.code = "0";
        }
        if (StringUtils.isBlank(this.message)) {
            this.message = "true";
        }
        return this;
    }

    private ResponseImpl<T> error() {
        if (StringUtils.isBlank(this.code)) {
            this.code = "1";
        }
        if (StringUtils.isBlank(this.message)) {
            this.message = "false";
        }
        return this;
    }

    public static <T> ResponseImpl<T> full() {
        return new ResponseImpl<T>().error();
    }

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }
}
