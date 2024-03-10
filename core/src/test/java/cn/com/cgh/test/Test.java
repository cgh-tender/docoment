package cn.com.cgh.test;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        ArrayList<Object> list = new ArrayList<>();

        Pojo pojo = new Pojo();
        pojo.setId(1);
        list.add(pojo);
        pojo = new Pojo();
        pojo.setId(2);
        list.add(pojo);
        System.out.println(JSONUtil.toJsonStr(list));
    }
}
@Getter
@Setter
class Pojo{
    private int id = 0;
    private String userId = "0";

}
