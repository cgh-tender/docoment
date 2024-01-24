package cn.com.cgh.core.config.db;

import cn.hutool.json.JSONUtil;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.Map;

@Slf4j
public class DataSourceUtil {
    @Resource
    DynamicDataSource dynamicDataSource;

    /**
     * 测试数据源是否可用，如果可用即直接返回
     *
     * @param dataSourceInfo
     * @return
     * @throws SQLException
     */
    public HikariDataSource createDataSourceConnection(MyDataSourceProperties dataSourceInfo){
        //创建数据源对象
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(dataSourceInfo.getUrl());
        hikariDataSource.setUsername(dataSourceInfo.getUsername());
        hikariDataSource.setPassword(dataSourceInfo.getPassword());
        hikariDataSource.setConnectionTestQuery("select 1");
        hikariDataSource.setMaximumPoolSize(15);
        hikariDataSource.setValidationTimeout(3000);
        hikariDataSource.setReadOnly(false);
        hikariDataSource.setConnectionTimeout(60000);
        hikariDataSource.setIdleTimeout(60000);
        hikariDataSource.setMaxLifetime(60000);
        hikariDataSource.setKeepaliveTime(30000);
        try {
            //尝试连接数据源
            hikariDataSource.getConnection();
            log.info("数据源:{}连接成功", JSONUtil.toJsonStr(dataSourceInfo));
            return hikariDataSource;
        } catch (SQLException e) {
            log.error("数据源 {} 连接失败,用户名：{}，密码 {}",dataSourceInfo.getUrl(),dataSourceInfo.getUsername(),dataSourceInfo.getPassword());
            return null;
        }
    }

    /**
     * 将外部数据源存到dynamicDataSource并调用afterPropertiesSet刷新
     * @param druidDataSource
     * @param dataSourceName
     */
    public void addDefineDynamicDataSource(HikariDataSource druidDataSource, String dataSourceName){
        Map<Object, Object> defineTargetDataSources = dynamicDataSource.getDefineTargetDataSources();
        //存到defineTargetDataSources这个map中
        defineTargetDataSources.put(dataSourceName, druidDataSource);
        dynamicDataSource.setTargetDataSources(defineTargetDataSources);
        //调用afterPropertiesSet重新遍历map中的数据源键值对存到resolvedDataSources中
        dynamicDataSource.afterPropertiesSet();
    }
}
