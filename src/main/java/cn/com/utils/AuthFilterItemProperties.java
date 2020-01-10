package cn.com.utils;

import cn.com.utils.interfaceRun.YmlPropertySourceComponent;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@YmlPropertySourceComponent(value = {"classpath:/resources/AuthFilter.yml","classpath:auth.properties"})
@ConfigurationProperties(prefix = "data")
@Data
public class AuthFilterItemProperties {
    private Map<Object,Object> filterChainDefinitionMap;
    private String hashAlgorithmName;
    private int isSeparation;
    private String isSeparationDesc = isSeparationEnum.getDesc(isSeparation);
    enum isSeparationEnum{
        isSeparation_0(0,"两者并存"),isSeparation_1(1,"存在Session的方式"),isSeparation_2(2,"不存在Session的方式");
        private int isSeparation;
        private String Desc;
        private isSeparationEnum(int isSeparation, String Desc){
            this.isSeparation = isSeparation;
            this.Desc = Desc;
        }

        public int getIsSeparation() {
            return isSeparation;
        }

        public void setIsSeparation(int isSeparation) {
            this.isSeparation = isSeparation;
        }

        public String getDesc() {
            return Desc;
        }

        public void setDesc(String desc) {
            Desc = desc;
        }
        public static String getDesc(int code) {
            return isSeparationEnum.valueOf("isSeparation_"+code).getDesc();
        }

        @Override
        public String toString() {
            return this.Desc;
        }
    }
}
