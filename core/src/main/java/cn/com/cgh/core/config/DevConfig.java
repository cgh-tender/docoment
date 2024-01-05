package cn.com.cgh.core.config;

import org.springframework.context.annotation.Profile;

@Profile(value = {"dev","test"})
public class DevConfig {

}
