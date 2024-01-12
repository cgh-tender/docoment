package cn.com.cgh.auth.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "AuthController", description = "认证中心登录认证")
@Slf4j
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest loginRequest) {
        Authentication authenticationRequest = UsernamePasswordAuthenticationToken
                .authenticated(loginRequest.username(), loginRequest.password(), null);
        Authentication authenticationResponse =
                this.authenticationManager.authenticate(authenticationRequest);
        boolean authenticated = authenticationResponse.isAuthenticated();
        if (!authenticated) {
            throw new RuntimeException("authentication failed");
        }
        log.info("login");
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/hello")
    public String hello() {
        return "helloWorld";
    }

    public record LoginRequest(String username, String password) {
    }
}
