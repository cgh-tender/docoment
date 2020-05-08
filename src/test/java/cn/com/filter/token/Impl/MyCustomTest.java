package cn.com.filter.token.Impl;

import cn.com.filter.token.Body.Impl.TokenUserNamePayload;
import cn.com.utils.Redis.RedisUtil;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

@SpringBootTest
@Log4j
@RunWith(SpringRunner.class)
public class MyCustomTest {
    private MockHttpServletRequest request;
    @Resource
    private ToeknFactory toeknFactory;
    @Resource
    private RedisUtil redisUtil;
    @Before
    public void setUp(){
        request = new MockHttpServletRequest();
        request.setCharacterEncoding("UTF-8");
//        response = new MockHttpServletResponse();
    }

    @SneakyThrows
    @Test
    public void main1() {
        for (int i = 0; i < 3; i++) {
            String key = "springContextUtil.getTokenSalt()" + "id";
            KeyGenerator keygen= KeyGenerator.getInstance("AES");
            keygen.init(256, new SecureRandom(key.getBytes()));
            SecretKey original_key=keygen.generateKey();
            byte [] raw=original_key.getEncoded();
            log.info(new SecretKeySpec(raw, "AES"));

        }
//
//        String token = toeknFactory.getInstance().builder(new TokenUserNamePayload("admin","1" ,request)).getToken();
//        log.info(token);
//        log.info(toeknFactory.getInstance().builder(token).decodeToken());
//        log.info(toeknFactory.getInstance().builder(token).getClaims());
//        log.info(toeknFactory.getInstance().builder(token).saveToken());
//        try {
//            Thread.sleep(1000*10);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        log.info(redisUtil.getExpire("admin"));
////        log.info(redisUtil.getExpire(token + "_keyPair"));
//        log.info(toeknFactory.getInstance().builder(token).isOverdue());
//        String reToken = toeknFactory.getInstance().builder(token).reToken();
//        log.info(reToken);
//        log.info(toeknFactory.getInstance().builder(reToken).decodeToken());
//        log.info(toeknFactory.getInstance().builder(reToken).getClaims());
    }

    @SneakyThrows
    public static void main(String[] args) {

//        KeyGenerator keyGen = KeyGenerator.getInstance("AES");//密钥生成器
//        keyGen.init(128); //默认128，获得无政策权限后可为192或256
//        SecretKey secretKey = keyGen.generateKey();//生成密钥
//        byte[] key = secretKey.getEncoded();//密钥字节数组
//        SecretKey secretKey1 = new SecretKeySpec(key, "AES");//恢复密钥
//
//
//        Cipher cipher1 = Cipher.getInstance("AES");//Cipher完成加密或解密工作类
//        cipher1.init(Cipher.DECRYPT_MODE, secretKey1);//对Cipher初始化，解密模式
//        byte[] cipherByte = cipher1.doFinal(data);//解密data


    }
}