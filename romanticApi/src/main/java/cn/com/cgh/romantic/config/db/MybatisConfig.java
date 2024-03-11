package cn.com.cgh.romantic.config.db;

import cn.com.cgh.romantic.handler.DefaultDBFieldHandler;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static cn.com.cgh.romantic.constant.RomanticConstant.MASTER;
import static cn.com.cgh.romantic.constant.RomanticConstant.SLAVE1;

@Configuration
@MapperScan(basePackages = {"cn.com.cgh.**.mapper"})
@Getter
@Setter
@Slf4j
@AutoConfigureBefore(value = {MybatisPlusAutoConfiguration.class})
@EnableTransactionManagement
public class MybatisConfig {
    private static final ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
    static {
        log.info("MybatisConfig:已启动");
    }

    @Bean(name = MASTER)
    @ConfigurationProperties("spring.datasource.master")
    @RefreshScope
    public DataSource masterDataSource()
    {
        return DataSourceBuilder.create().build();
    }
    @Bean(name = SLAVE1)
    @ConfigurationProperties("spring.datasource.slave1")
    @RefreshScope
    public DataSource slave1DataSource()
    {
        return DataSourceBuilder.create().build();
    }
//    @Bean(name = Constants.SLAVE2)
//    @ConfigurationProperties("spring.datasource.druid.slave2")
//    @RefreshScope
//    public DataSource slave2DataSource()
//    {
//        return DataSourceBuilder.create().build();
//    }

    @Bean
    @Primary
    public DynamicDataSource dynamicDataSource()
    {
        //创建一个存放数据源的map
        Map<Object, Object> dataSourceMap = new HashMap<>(4);
        //将上述两个数据源存放到map中
        dataSourceMap.put(MASTER,masterDataSource());
        dataSourceMap.put(SLAVE1,slave1DataSource());
//        dataSourceMap.put(Constants.SLAVE2,slave2DataSource());

        //设置动态数据源，默认为master配置的数据源，并指定数据源的map
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setDefaultTargetDataSource(masterDataSource());
        dynamicDataSource.setTargetDataSources(dataSourceMap);

        //将数据源信息备份在defineTargetDataSources中
        dynamicDataSource.setDefineTargetDataSources(dataSourceMap);

        return dynamicDataSource;
    }

    @Bean(name = "globalConfig")
    public GlobalConfig globalConfig(DefaultDBFieldHandler defaultDBFieldHandler, CustomIdGenerator customIdGenerator) {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setMetaObjectHandler(defaultDBFieldHandler);
        globalConfig.setIdentifierGenerator(customIdGenerator);
        return globalConfig;
    }

    @Bean
    public PaginationInnerInterceptor pageInnerInterceptor() {
        return new PaginationInnerInterceptor(DbType.MYSQL);
    }
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(PaginationInnerInterceptor pageInnerInterceptor) {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(pageInnerInterceptor);
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource masterDataSource, GlobalConfig globalConfig,MybatisPlusInterceptor mybatisPlusInterceptor) throws Exception {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(masterDataSource);
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        factoryBean.setConfiguration(configuration);
        factoryBean.setPlugins(mybatisPlusInterceptor);
        factoryBean.setMapperLocations(resourceResolver.getResources("classpath*:mapper/**/*.xml"));
        factoryBean.setGlobalConfig(globalConfig);
        factoryBean.setTypeHandlersPackage("cn.com.cgh.**.typeHandler");
        factoryBean.setTransactionFactory(new SpringManagedTransactionFactory());
        return factoryBean.getObject();
    }



    @Bean
    public JdbcTemplate jdbcTemplate(DataSource master){
        return new JdbcTemplate(master);
    }
}
