package cn.com.utils.sql;

import io.shardingjdbc.core.constant.DatabaseType;
import io.shardingjdbc.core.constant.SQLType;
import io.shardingjdbc.core.jdbc.core.ShardingContext;
import io.shardingjdbc.core.parsing.SQLJudgeEngine;
import io.shardingjdbc.core.parsing.SQLParsingEngine;
import io.shardingjdbc.core.parsing.lexer.LexerEngine;
import io.shardingjdbc.core.parsing.lexer.LexerEngineFactory;
import io.shardingjdbc.core.parsing.parser.context.table.Tables;
import io.shardingjdbc.core.parsing.parser.sql.SQLParser;
import io.shardingjdbc.core.parsing.parser.sql.SQLParserFactory;
import io.shardingjdbc.core.parsing.parser.sql.SQLStatement;
import io.shardingjdbc.core.rule.ShardingRule;
import lombok.extern.log4j.Log4j;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.Queue;

@Log4j
public class ShardingSqlUtil {
    public ShardingRule getShardingRule(){
//        @SuppressWarnings("resource")
//        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("application.xml");
//        SpringShardingDataSource dataSource = ctx.getBean(SpringShardingDataSource.class);
//        Field field = SpringShardingDataSource.class.getSuperclass().getDeclaredField("shardingContext");
//        field.setAccessible(true);
//        ShardingContext sctx = (ShardingContext)field.get(dataSource);
//        ShardingRule shardingRule = sctx.getShardingRule();
        return null;
    }
    public void test(DatabaseType databaseType,String sql){
//        ShardingRuleMockBuilder shardingRuleMockBuilder = new ShardingRuleMockBuilder();
        LexerEngine lexerEngine = LexerEngineFactory.newInstance(databaseType,sql);
        lexerEngine.nextToken();
        SQLParser sqlParser = SQLParserFactory.newInstance(
                databaseType,
                lexerEngine.getCurrentToken().getType(), getShardingRule(), lexerEngine);
        SQLStatement result = sqlParser.parse();
        log.info(lexerEngine.getCurrentToken().getType());
        SQLJudgeEngine sqlJudgeEngine = new SQLJudgeEngine(sql);
        SQLStatement judge = sqlJudgeEngine.judge();
        Tables tables = judge.getTables();
        SQLType type = judge.getType();
        log.info("tables :"+ tables);
    }


    public Queue<String> getSqls(String filePath) {
        BufferedReader reader = null;
        Queue<String> sqls = new ArrayDeque<String>();
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tem = "";
            int i = 1;
            StringBuffer sql = new StringBuffer();
            while ((tem = reader.readLine()) != null) {
                tem = tem.replaceAll("\\-\\-.*", "").trim();
                if (!tem.isEmpty()) {
                    sql.append(" ");
                    sql.append(tem);
                }
                if (tem.contains(";")) {
                    sqls.add(sql.toString());
                    sql = new StringBuffer();
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sqls;
    }
}
