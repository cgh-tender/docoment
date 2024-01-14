package cn.com.cgh.auth.controller;

import cn.com.cgh.romantic.login.ILoginController;
import cn.com.cgh.romantic.login.IUmsUserServer;
import cn.com.cgh.romantic.pojo.UserDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Tag(name = "AuthController", description = "认证中心登录认证")
@Slf4j
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ILoginController iLoginController;

    @PostMapping("/login")
    public Map login(@RequestBody UserDto loginRequest) {
        log.info("===========");
        Authentication authenticationRequest = UsernamePasswordAuthenticationToken
                .authenticated(loginRequest.getUsername(), loginRequest.getPassword(), null);
        Authentication authenticationResponse =
                this.authenticationManager.authenticate(authenticationRequest);
        boolean authenticated = authenticationResponse.isAuthenticated();
        if (!authenticated) {
            throw new RuntimeException("authentication failed");
        }
        log.info("login");
        return iLoginController.login(loginRequest).getData();
    }

    @GetMapping("/error")
    public String error(){
        return "error";
    }

    @GetMapping("/hello")
    public String hello() {
        return "helloWorld";
    }

    public record LoginRequest(String username, String password) {
    }
}
