package cn.com.utils.sql.parser;

import cn.com.utils.sql.ParserSql;
/**
 *  定义sql类型
 */
public enum SqlParseType {
    ALTER("修改(ALTER)",new SqlParserImpl.PAlter()),
    CREATEINDEX("创建索引",new SqlParserImpl.PCreateIndex()),
    CREATETABLE("创建表", new SqlParserImpl.PCreateTable()),
    CREATEVIEW("创建视图", new SqlParserImpl.PCreateView()),
    DELETE("删除", new SqlParserImpl.PDelete()),
    DROP("清空(DROP)",new SqlParserImpl.PDrop()),
    EXECUTE("执行",new SqlParserImpl.PExecute()),
    INSERT("插入",new SqlParserImpl.PInsert()),
    MERGE("合并", new SqlParserImpl.PMerge()),
    REPLACE("替换",new SqlParserImpl.PMerge()),
    SELECT("查询",new SqlParserImpl.PSelect()),
    TRUNCATE("清空(TRUNCATE)",new SqlParserImpl.PTruncate()),
    UPDATE("修改(UPDATE)",new SqlParserImpl.PUpdate()),
    UPSERT("组合(UPSERT)",new SqlParserImpl.PUpsert()),
    NONE("无效(NONE)",new SqlParserImpl.POther());

    private SqlParseType(String desc, ParserSql statement){
        this.desc = desc;
        this.statement = statement;
    }

    public ParserSql getStatement() {
        return statement;
    }
    @Override
    public String toString() {
        return "当前为［" + this.desc + "］语句";
    }
    private String desc;
    private ParserSql statement;
}
