package cn.com.cgh.resource;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.zaxxer.hikari.HikariDataSource;

public class CodeGen {
    public static void main(String[] args) {

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/cgh1?characterEncoding=utf-8");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        // 代码生成器


        // 全局配置
        GlobalConfig config = new GlobalConfig.Builder()
                .outputDir("/Users/cgh/worker/docoment/login/src/main/java/").enableSwagger().commentDate("yyyy-MM-dd").build();

        PackageConfig pc = new PackageConfig.Builder().parent("cn.com.cgh.login.auth")
                .entity("entity").mapper("mapper").service("service").controller("controller")
                .build();
        AutoGenerator auto = new AutoGenerator(new DataSourceConfig.Builder(dataSource).build()).global(config)
                .packageInfo(pc);


        // 策略配置
        StrategyConfig strategy = new StrategyConfig.Builder().addInclude("sys_resource").entityBuilder()
               .enableLombok().enableChainModel().enableRemoveIsPrefix().enableTableFieldAnnotation().build();

        auto.strategy(strategy);
        auto.execute();
    }

}
