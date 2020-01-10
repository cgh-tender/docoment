package cn.com.entity;

import lombok.extern.log4j.Log4j;

@Log4j
public enum RestStatus {
    LOGOUT("已经登出",-1),LOGIN("成功登录",1),
    EX("错误",2),EXIP("当前 IP于登录不一至",3);

    private String name;
    private int code;

    private RestStatus(String name,int code){
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}