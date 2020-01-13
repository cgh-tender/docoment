package cn.com.utils.ex;

import cn.com.filter.token.JwtToken;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
@Log4j
public class JwtException extends BaseExceptions {
    @ExceptionHandler({ExpiredJwtException.class})
    public void exception(ExpiredJwtException ex) {
        log.info(ex.getClaims().get(JwtToken.ClaimName));
    }

}
