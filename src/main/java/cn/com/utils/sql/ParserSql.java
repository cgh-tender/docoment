package cn.com.utils.sql;

import net.sf.jsqlparser.statement.Statement;

import java.util.Map;

public interface ParserSql {
    Map<String,Object> parser(Statement statement);
}
