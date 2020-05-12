package cn.com.utils.sql.parser;

import cn.com.utils.sql.ParserSql;
import cn.com.utils.sql.SqlParserUtil;
import com.alibaba.fastjson.JSONObject;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.alter.Alter;
import net.sf.jsqlparser.statement.create.index.CreateIndex;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.create.view.CreateView;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.drop.Drop;
import net.sf.jsqlparser.statement.execute.Execute;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.merge.Merge;
import net.sf.jsqlparser.statement.replace.Replace;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.truncate.Truncate;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.upsert.Upsert;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

/**
 * sql 解析类
 */
public class SqlParserFactory {
    private final Logger log = Logger.getLogger(SqlParserFactory.class);
    private File file;
    private String sqlData;

    private SqlParserFactory(){}

    /**
     * sql 文件
     * @param file
     */
    public SqlParserFactory(File file){
        this.file = file;
    }

    /**
     * 单条 sql
     * @param sql
     */
    public SqlParserFactory(String sql){
        this.sqlData = sql;
    }

    /**
     * 运行入口
     */
    public void run(){
        //文件类型的解析
        if (file != null){
            Queue<String> sqls = SqlParserUtil.getSqls(file);
            int size = sqls.size();
            for (int i = 1; i <= size; i++) {
                String sql = sqls.poll();
                HashMap<String, Object> data = parserSql(sql);
                log.info(data.get("SQL"));
                data.remove("SQL");
                log.info(JSONObject.toJSONString(data));
            }
            //单sql解析
        }else if (sqlData != null){
            HashMap<String, Object> data = parserSql(SqlParserUtil.getSql(sqlData));
            log.info(data.get("SQL"));
            data.remove("SQL");
            log.info(data);
        }
    }

    /**
     * 解析 sql 内容
     * @param sql
     */
    private HashMap<String, Object> parserSql(String sql){
        HashMap<String, Object> map = new HashMap<>();
        try {
            map.put("SQL",sql);
            SqlParseType sqlType = getSqlType(sql);
            map.put("TYPE",sqlType.toString());
            ParserSql statement = sqlType.getStatement();
            Map<String, Object> parser = statement.parser(SqlParserUtil.getStatement(sql));
            map.put("PARSER",parser);
            map.put("SUCCESS",true);
        } catch (JSQLParserException e) {
            Throwable cause = e.getCause();
            map.put("SUCCESS",false);
            map.put("MSG","\n异常情况 请检查: \n"+cause.getMessage().split("\\.")[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * <p> 获取 sql 的查询类型
     *
     * @param sql
     * @return SqlParseType
     * @throws JSQLParserException
     */
    private SqlParseType getSqlType(String sql) throws JSQLParserException {
        final Statement sqlStmt = SqlParserUtil.getStatement(sql);
        if (sqlStmt instanceof Alter) {
            return SqlParseType.ALTER;
        } else if (sqlStmt instanceof CreateIndex) {
            return SqlParseType.CREATEINDEX;
        } else if (sqlStmt instanceof CreateTable) {
            return SqlParseType.CREATETABLE;
        } else if (sqlStmt instanceof CreateView) {
            return SqlParseType.CREATEVIEW;
        } else if (sqlStmt instanceof Delete) {
            return SqlParseType.DELETE;
        } else if (sqlStmt instanceof Drop) {
            return SqlParseType.DROP;
        } else if (sqlStmt instanceof Execute) {
            return SqlParseType.EXECUTE;
        } else if (sqlStmt instanceof Insert) {
            return SqlParseType.INSERT;
        } else if (sqlStmt instanceof Merge) {
            return SqlParseType.MERGE;
        } else if (sqlStmt instanceof Replace) {
            return SqlParseType.REPLACE;
        } else if (sqlStmt instanceof Select) {
            return SqlParseType.SELECT;
        } else if (sqlStmt instanceof Truncate) {
            return SqlParseType.TRUNCATE;
        } else if (sqlStmt instanceof Update) {
            return SqlParseType.UPDATE;
        } else if (sqlStmt instanceof Upsert) {
            return SqlParseType.UPSERT;
        } else {
            return SqlParseType.NONE;
        }
    }

}
