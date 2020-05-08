package cn.com.utils.sql;

import io.shardingjdbc.core.api.config.ShardingRuleConfiguration;
import io.shardingjdbc.core.constant.DatabaseType;
import io.shardingjdbc.core.rule.ShardingRule;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Queue;

@SpringBootTest
@SpringJUnitConfig
@Log4j
public class ShardingSqlUtilTest {

    @Test
    public void sqlTest(){

        ShardingSqlUtil shardingSqlUtil1 = new ShardingSqlUtil();
        Queue<String> sqls = shardingSqlUtil1.getSqls("C:\\Users\\HaiDar\\Desktop\\联通欠费.txt");
        int size = sqls.size();
        for (int i = 1; i <= size; i++) {
            String data = sqls.poll();
            log.info(i + ": " + data);
            ShardingSqlUtil shardingSqlUtil = new ShardingSqlUtil();
            shardingSqlUtil.test(DatabaseType.Oracle,data);
        }
    }
}