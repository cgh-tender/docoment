package cn.com.cgh.test;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

public class Test {
    public static void main(String[] args) {
        Pojo pojo = new Pojo();
        pojo.setId(null);
        String json = JSONObject.toJSONString(pojo);
        Pojo bean = JSONUtil.parseObj(json).toBean(Pojo.class);
        System.out.println(bean);
    }
}
@Getter
@Setter
class Pojo{
    private String id = "0";
    private String userId = "0";

    public int setIntId(String id) {
        return Integer.parseInt(id);
    }
}
