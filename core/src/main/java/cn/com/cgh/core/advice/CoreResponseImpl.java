package cn.com.cgh.core.advice;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
class CoreResponseImpl<T> {
    private String code;
    private String message;
    @Builder.Default
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime timestamp = LocalDateTime.now();
    private T data;

    public CoreResponseImpl<T> SUCCESS(){
        if (StringUtils.isBlank(this.code)){
            this.code = "0";
        }
        if (StringUtils.isBlank(this.message)){
            this.message = "success";
        }
        return this;
    }
    public CoreResponseImpl<T> FULL() {
        if (StringUtils.isBlank(this.code)) {
            this.code = "1";
        }
        if (StringUtils.isBlank(this.message)) {
            this.message = "full";
        }
        return this;
    }

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }
}
