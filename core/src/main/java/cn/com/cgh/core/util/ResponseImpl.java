package cn.com.cgh.core.util;

import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ResponseImpl<T> {
    private String code;
    private String message;
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
    private T data;

    public ResponseImpl<T> SUCCESS(){
        if (StringUtils.isBlank(this.code)){
            this.code = "0";
        }
        if (StringUtils.isBlank(this.message)){
            this.message = "success";
        }
        return this;
    }
    public ResponseImpl<T> FULL() {
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
