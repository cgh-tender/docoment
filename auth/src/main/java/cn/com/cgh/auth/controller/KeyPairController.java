package cn.com.cgh.auth.controller;

import cn.com.cgh.auth.component.KeyStoreUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.Map;

@RestController
@Tag(name = "KeyPairController", description = "获取RSA公钥接口")
@RequestMapping("/rsa")
public class KeyPairController {

    private KeyStoreUtil keyStoreUtil;

    public void start() {
        try {
            String keystorePath = "/path/to/keystore.jks";
            String keystorePassword = "keystorePassword";
            String keyAlias = "keyAlias";

            KeyStore.Entry keyEntry = keyStoreUtil.getKeyEntry(keystorePath, keystorePassword, keyAlias);
            PrivateKey privateKey = keyStoreUtil.getPrivateKey(keystorePath, keystorePassword, keyAlias);
            Certificate certificate = keyStoreUtil.getCertificate(keystorePath, keystorePassword, keyAlias);
//            certificate.getPublicKey()
            System.out.println("PrivateKey: " + privateKey);
            System.out.println("Certificate: " + certificate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/publicKey")
    public Map<String, Object> getKey() {
//        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
//        RSAKey key = new RSAKey.Builder(publicKey).build();
//        return new JWKSet(key).toJSONObject();
        return null;
    }

}