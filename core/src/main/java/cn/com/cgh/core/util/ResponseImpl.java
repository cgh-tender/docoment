package cn.com.cgh.core.util;

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
    private String msg;
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
    private T data;

    public ResponseImpl<T> SUCCESS(){
        if (StringUtils.isBlank(this.code)){
            this.code = "0";
        }
        if (StringUtils.isBlank(this.msg)){
            this.msg = "success";
        }
        return this;
    }
    public ResponseImpl<T> FULL() {
        if (StringUtils.isBlank(this.code)) {
            this.code = "1";
        }
        if (StringUtils.isBlank(this.msg)) {
            this.msg = "full";
        }
        return this;
    }
}
