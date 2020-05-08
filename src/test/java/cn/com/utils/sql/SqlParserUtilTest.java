package cn.com.utils.sql;

import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.io.File;

@SpringBootTest
@SpringJUnitConfig
@Log4j
public class SqlParserUtilTest {
    @Test
  public void sqlTest() {
        File file = new File("C:\\Users\\HaiDar\\Desktop\\联通欠费.txt");
        new SqlParserUtil(file).run();
        log.info("=========================");
        String sql = "Create Table DW_OWE_BILL_BSS_07_all as(     -- 王者 \n" +
                "/*\n" +
                "/*\n" +
                "aaa\n" +
                "*/\n" +
                "*/\n" +
                "SELECT *\n" +
                "  FROM (SELECT a.subs_instance_id user_id,\n" +
                "               MONTHS_BETWEEN(to_date(max(owe_month), 'yyyymm'),\n" +
                "                              to_date(min(owe_month), 'yyyymm'))+1 cnt,\n" +
                "               sum(owe_fee) owe_fee_all,\n" +
                "               max(owe_month) max_owe_month,\n" +
                "               min(owe_month) min_owe_month\n" +
                "          FROM DW_OWE_BILL_BSS_07 a\n" +
                "         GROUP BY subs_instance_id) a\n" +
                " right join DW_OWE_BILL_BSS_07 b\n" +
                "    on a.user_id = b.subs_instance_id);";
        new SqlParserUtil(sql).run();
    }
}