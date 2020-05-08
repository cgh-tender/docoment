package cn.com.utils.sql;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.*;
import net.sf.jsqlparser.statement.alter.*;
import net.sf.jsqlparser.statement.create.index.CreateIndex;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.create.view.CreateView;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.drop.Drop;
import net.sf.jsqlparser.statement.execute.Execute;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.merge.Merge;
import net.sf.jsqlparser.statement.replace.Replace;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.statement.truncate.Truncate;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.upsert.Upsert;
import net.sf.jsqlparser.util.TablesNamesFinder;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.*;

public class SqlParserUtil<T> {
    private static final Logger log = Logger.getLogger(SqlParserUtil.class);

    private File file;
    private String sqlData;
    private SqlParserUtil(){}

    public void run(){
        if (file != null){
            Queue<String> sqls = getSqls(file);
            int size = sqls.size();
            for (int i = 1; i <= size; i++) {
                String sql = sqls.poll();
                this.SqlParser(sql);
            }
        }else if (sqlData != null){
            this.SqlParser(sqlData);
        }
    }

    public SqlParserUtil(File file){
        this.file = file;
    }

    public SqlParserUtil(String sql){
        this.sqlData = sql;
    }

    /**
     * <p> 获取 sql 的查询类型
     *
     * @param sql
     * @return SqlParseType
     * @throws JSQLParserException
     */
    public static SqlParseType getSqlType(String sql) throws JSQLParserException {
        final Statement sqlStmt = getStatement(sql);
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

    public static Map<String, Object> parserSql(String sql) throws JSQLParserException {
        log.info("当前解析的sql:［" + sql  + "］");
        SqlParseType sqlType = getSqlType(sql);
        log.info(sqlType.toString());
        ParserSql statement = sqlType.getStatement();
        Map<String, Object> parser = statement.parser(getStatement(sql));
        return parser;
    }

    private void SqlParser(String sql){
        try {
            Map map = this.parserSql(SqlParserUtil.getSql(sql));
            log.info("解析内容: ");
            log.info(map);
        } catch (JSQLParserException e) {
            Throwable cause = e.getCause();
            log.error("\n异常情况 请检查: \n"+cause.getMessage().split("\\.")[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class PAlter extends Alter implements ParserSql{
        @Override
        public Map<String, Object> parser(Statement statement) {
            HashMap<String, Object> stringStringHashMap = new HashMap<>();
            Alter parserSql1 = (Alter) statement;
            try{
                Table table = parserSql1.getTable();
                stringStringHashMap.put("TABLENAME",table.getName());
                stringStringHashMap.put("fiedName",table.getFullyQualifiedName());
            }catch (Exception e){}
            return stringStringHashMap;
        }
    }
    static class PCreateIndex extends CreateIndex implements ParserSql{
        @Override
        public Map<String, Object> parser(Statement statement) {
            return null;
        }
    }
    static class PCreateTable extends CreateTable implements ParserSql{
        @Override
        public Map<String, Object> parser(Statement statement) {
            HashMap<String, Object> stringStringHashMap = new HashMap<>();
            CreateTable parserSql1 = (CreateTable) statement;
            try{
                stringStringHashMap.put("TABLENAME",getTableList(parserSql1).toString());
            }catch (Exception e){}
            try{
                if (null != parserSql1.getSelect()){
                    stringStringHashMap.put("Columns",getSelectItems(parserSql1.getSelect().getSelectBody()));
                }else {
                    stringStringHashMap.put("Columns",parserSql1.getColumnDefinitions().toString());
                }

            }catch (Exception e){}
            return stringStringHashMap;
        }
    }
    static class PCreateView extends CreateView implements ParserSql{
        @Override
        public Map<String, Object> parser(Statement statement) {
            HashMap<String, Object> stringStringHashMap = new HashMap<>();
            return stringStringHashMap;
        }
    }
    static class PDelete extends Delete implements ParserSql{
        @Override
        public Map<String, Object> parser(Statement statement) {
            HashMap<String, Object> stringStringHashMap = new HashMap<>();
            return stringStringHashMap;
        }
    }
    static class PDrop extends Drop implements ParserSql{
        @Override
        public Map<String, Object> parser(Statement statement) {
            HashMap<String, Object> stringStringHashMap = new HashMap<>();
            Drop parserSql1 = (Drop) statement;
            try{
                stringStringHashMap.put("TABLENAME",parserSql1.getName());
            }catch (Exception e){}
            try{
                String type = parserSql1.getType();
                stringStringHashMap.put("Type",type);
            }catch (Exception e){}
            return stringStringHashMap;
        }
    }
    static class PExecute extends Exception implements ParserSql{
        @Override
        public Map<String, Object> parser(Statement statement) {
            HashMap<String, Object> stringStringHashMap = new HashMap<>();
            return stringStringHashMap;
        }
    }
    static class PInsert extends Insert implements ParserSql{
        @Override
        public Map<String, Object> parser(Statement statement) {
            HashMap<String, Object> stringStringHashMap = new HashMap<>();
            return stringStringHashMap;
        }
    }
    static class PMerge extends Merge implements ParserSql{
        @Override
        public Map<String, Object> parser(Statement statement) {
            HashMap<String, Object> stringStringHashMap = new HashMap<>();
            return stringStringHashMap;
        }
    }
    static class PReplace extends Replace implements ParserSql{
        @Override
        public Map<String, Object> parser(Statement statement) {
            HashMap<String, Object> stringStringHashMap = new HashMap<>();
            return stringStringHashMap;
        }
    }
    static class PSelect extends Select implements ParserSql{
        @Override
        public Map<String, Object> parser(Statement statement) {
            HashMap<String, Object> stringStringHashMap = new HashMap<>();
            return stringStringHashMap;
        }
    }static class PTruncate extends Truncate implements ParserSql{
        @Override
        public Map<String, Object> parser(Statement statement) {
            HashMap<String, Object> stringStringHashMap = new HashMap<>();
            return stringStringHashMap;
        }
    }static class PUpdate extends Update implements ParserSql{
        @Override
        public Map<String, Object> parser(Statement statement) {
            HashMap<String, Object> stringStringHashMap = new HashMap<>();
            return stringStringHashMap;
        }
    }
    static class PUpsert extends Upsert implements ParserSql{
        @Override
        public Map<String, Object> parser(Statement statement) {
            HashMap<String, Object> stringStringHashMap = new HashMap<>();
            return stringStringHashMap;
        }
    }
    static class POther implements ParserSql{
        @Override
        public Map<String, Object> parser(Statement statement) {
            HashMap<String, Object> stringStringHashMap = new HashMap<>();
            return stringStringHashMap;
        }
    }

    /**
     * <p> 返回当前sql的查询体
     * @param sql
     * @return Statement
     * @throws JSQLParserException
     */
    public static Statement getStatement(String sql) throws JSQLParserException {
        return CCJSqlParserUtil.parse(new StringReader(sql));
    }

    /**
     * <p> 获取tables的表名
     * @return List
     * @param statement
     */
    public static List<String> getTableList(Statement statement){
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        return tablesNamesFinder.getTableList(statement);
    }

    /**
     * <p> 获取子查询
     * @return SubSelect
     * @param selectBody
     */
    public static SubSelect getSubSelect(SelectBody selectBody){
        if(selectBody instanceof PlainSelect){
            FromItem fromItem = ((PlainSelect) selectBody).getFromItem();
            if(fromItem instanceof SubSelect){
                return ((SubSelect) fromItem);
            }
        }else if(selectBody instanceof WithItem){
            getSubSelect(((WithItem) selectBody).getSelectBody());
        }
        return null;
    }

    /**
     * <p> 获取join层级
     * @param selectBody
     * @return List<Join>
     */
    public static List<Join> getJoins(SelectBody selectBody) {
        if (selectBody instanceof PlainSelect) {
            List<Join> joins = ((PlainSelect) selectBody).getJoins();
            return joins;
        }
        return new ArrayList<Join>();
    }
    /**
     * <p>
     * @param selectBody
     * @return List<Table>
     */
    public static List<Table> getIntoTables(SelectBody selectBody){
        if(selectBody instanceof PlainSelect){
            return ((PlainSelect) selectBody).getIntoTables();
        }
        return new ArrayList<Table>();
    }
    /**
     * <p>
     * @param selectBody
     * @return
     */
    public static void setIntoTables(SelectBody selectBody,List<Table> tables){
        if(selectBody instanceof PlainSelect){
            ((PlainSelect) selectBody).setIntoTables(tables);
        }
    }

    /**
     * <p> 获取limit值
     * @param selectBody
     * @return Limit
     */
    public static Limit getLimit(SelectBody selectBody){
        if(selectBody instanceof PlainSelect){
            return ((PlainSelect) selectBody).getLimit();
        }
        return null;
    }

    /**
     * <p> 获取FromItem不支持子查询操作
     * @param selectBody
     * @return FromItem
     */
    public static FromItem getFromItem(SelectBody selectBody){
        if(selectBody instanceof PlainSelect){
            FromItem fromItem = ((PlainSelect) selectBody).getFromItem();
            return fromItem;
        }else if(selectBody instanceof WithItem){
            getFromItem(((WithItem) selectBody).getSelectBody());
        }
        return null;
    }

    /**
     * <p> 判断是否为多级子查询
     * <p>
     * @param selectBody
     * @return boolean
     */
    public static boolean isMultiSubSelect(SelectBody selectBody){
        if(selectBody instanceof PlainSelect){
            FromItem fromItem = ((PlainSelect) selectBody).getFromItem();
            if(fromItem instanceof SubSelect){
                SelectBody subBody = ((SubSelect) fromItem).getSelectBody();
                if(subBody instanceof PlainSelect){
                    FromItem subFromItem = ((PlainSelect) subBody).getFromItem();
                    if(subFromItem instanceof SubSelect){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * <p> 判断有多少层子查询
     * <p>
     * @param selectBody
     * @return boolean
     */
    public static int getMultiSubSelect(SelectBody selectBody){
        int i = 1;
        getMultiSubSelect(selectBody,i);
        return i;
    }

    public static void getMultiSubSelect(SelectBody selectBody,int i){
        if(selectBody instanceof PlainSelect){
            FromItem fromItem = ((PlainSelect) selectBody).getFromItem();
            if(fromItem instanceof SubSelect){
                i++;
                getMultiSubSelect(((SubSelect) fromItem).getSelectBody(), i);
            }
        }
    }

    /**
     * <p> 获取查询字段
     * @param selectBody
     * @return List<SelectItem>
     */
    public static List<SelectItem> getSelectItems(SelectBody selectBody){
        if(selectBody instanceof PlainSelect){
            List<SelectItem> selectItems = ((PlainSelect) selectBody).getSelectItems();
            return selectItems;
        }
        return null;
    }

    public static Queue<String> getSqls(File file) {
        BufferedReader reader = null;
        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        Queue<String> sqls = new ArrayDeque<String>();
        try {
            fileInputStream = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tem = "";
            int i = 1;
            StringBuffer sql = new StringBuffer();
            int nLine = 0;
            while ((tem = reader.readLine()) != null) {
//                -----------------------------------------------处理读取的数据-----------------------------------------------------------
                tem = tem.replaceAll("(--|#|\\/\\*.*\\*\\/).*", "").trim(); // 1 单选注释的方式
                // 多行注释
                if (tem.contains("/*")){
                    tem = tem.replaceAll("\\/\\*.*", "").trim();
                    nLine++;
                }
                if (nLine != 0 && tem.contains("*/")){
                    nLine--;
                    if (nLine == 0){
                        tem = tem.replaceAll(".*\\*\\/","").trim();
                    }
                }
                if (nLine != 0)continue;
//                ----------------------------------------------------------------------------------------------------------
                if (StringUtils.isNoneBlank(tem)) {
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
        }finally {
            try {
                fileInputStream.close();
            } catch (Exception e) {
            }
            try {
                inputStreamReader.close();
            } catch (Exception e) {
            }
            try {
                reader.close();
            } catch (Exception e) {
            }
        }
        return sqls;
    }

    public static String getSql(String sql) {
        if (StringUtils.isEmpty(sql)) return null;
        String[] split = sql.split("\\r|\\n");
        StringBuffer data = new StringBuffer();
        try {
            int nLine = 0;
            for (String tem: split) {
//                -----------------------------------------------处理读取的数据-----------------------------------------------------------
                tem = tem.replaceAll("(--|#|\\/\\*.*\\*\\/).*", "").trim(); // 1 单选注释的方式
                // 多行注释
                if (tem.contains("/*")){
                    tem = tem.replaceAll("\\/\\*.*", "").trim();
                    nLine++;
                }
                if (nLine != 0 && tem.contains("*/")){
                    nLine--;
                    if (nLine == 0){
                        tem = tem.replaceAll(".*\\*\\/","").trim();
                    }
                }
                if (nLine != 0)continue;
//                ----------------------------------------------------------------------------------------------------------
                if (StringUtils.isNoneBlank(tem)) {
                    data.append(" ");
                    data.append(tem);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data.toString();
    }
}
