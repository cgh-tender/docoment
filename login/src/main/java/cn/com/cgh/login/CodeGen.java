package cn.com.cgh.login;

import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.ColumnConfig;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.zaxxer.hikari.HikariDataSource;

public class CodeGen {
    public static void main(String[] args) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/cgh1?characterEncoding=utf-8");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");

        //创建配置内容，两种风格都可以。
        GlobalConfig globalConfig = createGlobalConfigUseStyle1();
        //GlobalConfig globalConfig = createGlobalConfigUseStyle2();

        //通过 datasource 和 globalConfig 创建代码生成器
        Generator generator = new Generator(dataSource, globalConfig);

        //生成代码
        generator.generate();
    }

    private static GlobalConfig createGlobalConfigUseStyle1() {
        //创建配置内容
        GlobalConfig globalConfig = new GlobalConfig();

        //设置根包
        globalConfig.setBasePackage("cn.com.cgh.login.auth");
        globalConfig.setEntityPackage("cn.com.cgh.login.auth.entity");
        globalConfig.setControllerPackage("cn.com.cgh.login.auth.controller");
        globalConfig.setServicePackage("cn.com.cgh.login.auth.server");
        globalConfig.setMapperPackage("cn.com.cgh.login.auth.mapper");
        //设置表前缀和只生成哪些表
        globalConfig.setTablePrefix("sys_");
        globalConfig.setGenerateTable("sys_resource");

        //设置生成 entity 并启用 Lombok
        globalConfig.setEntityGenerateEnable(true);
        globalConfig.setEntityWithLombok(true);

        //设置生成 mapper
        globalConfig.setMapperGenerateEnable(true);
        globalConfig.setControllerGenerateEnable(true);
        globalConfig.setServiceGenerateEnable(true);

        //可以单独配置某个列
        ColumnConfig columnConfig = new ColumnConfig();
        columnConfig.setColumnName("tenant_id");
        columnConfig.setLarge(true);
        columnConfig.setVersion(true);
        globalConfig.setColumnConfig("tb_account", columnConfig);

        return globalConfig;
    }
}
