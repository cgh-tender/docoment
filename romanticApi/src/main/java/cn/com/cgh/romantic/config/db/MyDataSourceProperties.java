package cn.com.cgh.romantic.config.db;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

@EqualsAndHashCode(callSuper = true)
@Data
public class MyDataSourceProperties extends DataSourceProperties {
    private String dataSourceKey;
}
