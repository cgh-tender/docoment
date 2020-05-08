package cn.com.utils.sql;

/**
 *  定义sql类型
 */
public enum SqlParseType {
    ALTER("修改(ALTER)",new SqlParserUtil.PAlter()),
    CREATEINDEX("创建索引",new SqlParserUtil.PCreateIndex()),
    CREATETABLE("创建表", new SqlParserUtil.PCreateTable()),
    CREATEVIEW("创建视图", new SqlParserUtil.PCreateView()),
    DELETE("删除", new SqlParserUtil.PDelete()),
    DROP("清空(DROP)",new SqlParserUtil.PDrop()),
    EXECUTE("执行",new SqlParserUtil.PExecute()),
    INSERT("插入",new SqlParserUtil.PInsert()),
    MERGE("合并", new SqlParserUtil.PMerge()),
    REPLACE("替换",new SqlParserUtil.PMerge()),
    SELECT("查询",new SqlParserUtil.PSelect()),
    TRUNCATE("清空(TRUNCATE)",new SqlParserUtil.PTruncate()),
    UPDATE("修改(UPDATE)",new SqlParserUtil.PUpdate()),
    UPSERT("组合(UPSERT)",new SqlParserUtil.PUpsert()),
    NONE("无效(NONE)",new SqlParserUtil.POther());

    private SqlParseType(String desc,ParserSql statement){
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
