package cn.com.cgh.core.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.Collections;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ResponseImpl {
    private String code;
    private String msg;
    private LocalDateTime timestamp = LocalDateTime.now();
    private Object data = Collections.EMPTY_MAP;

    public ResponseImpl SUCCESS(){
        if (StringUtils.isBlank(this.code)){
            this.code = "0";
        }
        if (StringUtils.isBlank(this.msg)){
            this.msg = "success";
        }
        return this;
    }
    public ResponseImpl FULL(){
        if (StringUtils.isBlank(this.code)){
            this.code = "1";
        }
        if (StringUtils.isBlank(this.msg)){
            this.msg = "full";
        }
        return this;
    }
}
