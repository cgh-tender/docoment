package cn.com.cgh.romantic.util;

import cn.com.cgh.romantic.em.DisplayedEnum;
import cn.com.cgh.romantic.pojo.SelectOption;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cgh
 */
public class DisplayedEnumUtil {
    public static List<SelectOption> parser(DisplayedEnum[] aa){
        return Arrays.stream(aa).map(s -> new SelectOption().setLabel(s.getLabel()).setValue(s.getValue())).collect(Collectors.toList());
    }

}
