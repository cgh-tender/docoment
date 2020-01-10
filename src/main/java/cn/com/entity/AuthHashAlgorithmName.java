package cn.com.entity;

import cn.com.filter.shiro.filter.MyHash;
import org.apache.shiro.crypto.hash.*;

public enum AuthHashAlgorithmName {
    MD5(new Md5Hash(),Md5Hash.ALGORITHM_NAME),MD2(new Md2Hash(),"MD2"),
    SHA1(new Sha1Hash(),"SHA1"),SHA256(new Sha256Hash(),"SHA256"),
    SHA384(new Sha384Hash(),"SHA384"),SHA512(new Sha512Hash(),"SHA512"),MY(new MyHash(),"MY");

    private SimpleHash simpleHash;
    private String name;

    private AuthHashAlgorithmName(SimpleHash simpleHash,String name){
        this.simpleHash = simpleHash;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SimpleHash getSimpleHash() {
        return simpleHash;
    }

    public void setSimpleHash(SimpleHash simpleHash) {
        this.simpleHash = simpleHash;
    }
}
