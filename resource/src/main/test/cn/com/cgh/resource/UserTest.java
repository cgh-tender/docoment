package cn.com.cgh.resource;

import cn.com.cgh.resource.auth.service.ITbCfgTableResourceService;
import cn.com.cgh.romantic.pojo.TbBaseEntity;
import cn.com.cgh.romantic.pojo.gateway.TbCfgError;
import cn.com.cgh.romantic.pojo.oasis.TbControllerLog;
import cn.com.cgh.romantic.pojo.oasis.TbLoginLog;
import cn.com.cgh.romantic.pojo.resource.*;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Slf4j
public class UserTest {
    @Autowired
    private ITbCfgTableResourceService tableResourceService;

    public List<Class<? extends TbBaseEntity>> a = Arrays.asList(
            TbCfgDatasource.class,
            TbCfgGroup.class,
            TbCfgOrganization.class,
            TbCfgPosition.class,
            TbCfgResource.class,
            TbCfgRole.class,
            TbCfgTableResource.class,
            TbCfgUser.class,
            TbRoleExclusion.class,
            TbRoleGroup.class,
            TbRoleOrganization.class,
            TbRolePosition.class,
            TbRoleResource.class,
            TbUserGroup.class,
            TbUserOrganization.class,
            TbUserPosition.class,
            TbUserRole.class,
            TbControllerLog.class,
            TbLoginLog.class,
            TbCfgError.class
    );

    @Test
    public void test() {
        for (Class<? extends TbBaseEntity> clazz : a) {
            Schema annotation = clazz.getAnnotation(Schema.class);
            TableName tableName =  clazz.getAnnotation(TableName.class);
            String tableCn = annotation.description();
            String tableEn = tableName.value();
            if (annotation != null){
                Field[] fields;
                Class<?> superclass = clazz.getSuperclass();
                List<TbCfgTableResource> arrayList = new ArrayList();
                if (superclass != null){
                    fields = superclass.getDeclaredFields();
                    for (Field field : fields) {
                        Schema schema = field.getAnnotation(Schema.class);
                        if (schema != null){
                            TbCfgTableResource build = TbCfgTableResource.builder().tableCn(tableCn).tableEn(tableEn)
                                    .filedCn(schema.description())
                                    .filedEn(field.getName())
                                    .filedDesc(schema.description())
                                    .filedPk(field.getName().equals("id") ? 1 : 0)
                                    .build();
                            arrayList.add(build);
                        }
                    }
                }
                fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    Schema schema = field.getAnnotation(Schema.class);
                    if (schema != null){
                        TbCfgTableResource build = TbCfgTableResource.builder().tableCn(tableCn).tableEn(tableEn)
                                .filedCn(schema.description())
                                .filedEn(field.getName())
                                .filedPk(field.getName().equals("id") ? 1 : 0)
                                .filedDesc(schema.description())
                                .build();
                        arrayList.add(build);
                    }
                }
                tableResourceService.saveBatch(arrayList);
            }
        }
    }
}
