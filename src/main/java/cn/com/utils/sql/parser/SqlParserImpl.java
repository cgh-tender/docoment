package cn.com.utils.sql.parser;

import cn.com.utils.sql.ParserSql;
import cn.com.utils.sql.SqlParserUtil;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.alter.Alter;
import net.sf.jsqlparser.statement.alter.AlterExpression;
import net.sf.jsqlparser.statement.create.index.CreateIndex;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.create.table.Index;
import net.sf.jsqlparser.statement.create.view.CreateView;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.drop.Drop;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.merge.Merge;
import net.sf.jsqlparser.statement.replace.Replace;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.statement.truncate.Truncate;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.upsert.Upsert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 解析不同sql实现类
 */
public class SqlParserImpl {
    private final static String TABLES = "TABLES";
    private final static String COLUMNS = "COLUMNS";
    private final static String INDEXNAME = "INDEXNAME";
    private final static String WHERE = "WHERE";
    private final static String ORDER = "ORDER";
    private final static String SEED = "SEED";
    private final static String ON = "ON";
    private final static String TYPE = "TYPE";
    private final static String DESC = "DESC";

    static class PAlter extends Alter implements ParserSql {
        @Override
        public Map<String, Object> parser(Statement statement) {
            HashMap<String, Object> sqlParaser = new HashMap<>();
            Alter statement1 = (Alter) statement;
            try{
                sqlParaser.put(TABLES,SqlParserUtil.getTableList(statement));
                List<AlterExpression> alterExpressions = statement1.getAlterExpressions();
                ArrayList<HashMap<String, String>> COLUMNNAMES = new ArrayList<HashMap<String, String>>();
                HashMap<String, String> data = null;
                for (AlterExpression alterExpression:alterExpressions){
                    data = new HashMap<String, String>();
                    String operation = alterExpression.getOperation().toString();
                    data.put(TYPE,operation);
                    String s = alterExpression.toString();
                    data.put(DESC,s);
                    COLUMNNAMES.add(data);
                }
                sqlParaser.put(COLUMNS,COLUMNNAMES);
            }catch (Exception e){ }
            return sqlParaser;
        }
    }
    static class PCreateIndex extends CreateIndex implements ParserSql{
        @Override
        public Map<String, Object> parser(Statement statement) {
            HashMap<String, Object> sqlParaser = new HashMap<>();
            CreateIndex parserSql1 = (CreateIndex) statement;
            sqlParaser.put(TABLES,SqlParserUtil.getTableList(statement));
            sqlParaser.put(COLUMNS,parserSql1.getIndex().getColumnsNames());
            sqlParaser.put(INDEXNAME,parserSql1.getIndex().getName());
            return sqlParaser;
        }
    }
    static class PCreateTable extends CreateTable implements ParserSql{
        @Override
        public Map<String, Object> parser(Statement statement) {
            HashMap<String, Object> sqlParaser = new HashMap<>();
            CreateTable statement1 = (CreateTable) statement;
            sqlParaser.put(TABLES, SqlParserUtil.getTableList(statement));
            try{
                if (SqlParserUtil.isNotEmpty(statement1.getSelect())){
                    SelectBody selectBody = statement1.getSelect().getSelectBody();
                    parserSelect(selectBody,sqlParaser);
                }else {
                    sqlParaser.put(COLUMNS,statement1.getColumnDefinitions().toString());
                }
                List<Index> indexes = statement1.getIndexes();
                if (SqlParserUtil.isNotEmpty(indexes)){
                    ArrayList<String> indexName = new ArrayList<>();
                    for (Index index:indexes) {
                        indexName.add(index.getName());
                    }
                    sqlParaser.put(INDEXNAME,indexName);
                }
            }catch (Exception e){}
            return sqlParaser;
        }
    }

    static class PCreateView extends CreateView implements ParserSql{
        @Override
        public Map<String, Object> parser(Statement statement) {
            HashMap<String, Object> sqlParaser = new HashMap<>();
            CreateView statement1 = (CreateView) statement;
            sqlParaser.put(TABLES,SqlParserUtil.getTableList(statement));
            Select select = statement1.getSelect();
            if (SqlParserUtil.isNotEmpty(select) && SqlParserUtil.isSelect(select.getSelectBody())){
                HashMap<String, Object> stringObjectHashMap = new HashMap<String, Object>();
                parserSelect(select.getSelectBody(),stringObjectHashMap);
                sqlParaser.put(SEED,stringObjectHashMap);
            }
            if (SqlParserUtil.isNotEmpty(statement1.getColumnNames()))sqlParaser.put(COLUMNS,statement1.getColumnNames());
            return sqlParaser;
        }
    }
    static class PDelete extends Delete implements ParserSql{
        @Override
        public Map<String, Object> parser(Statement statement) {
            HashMap<String, Object> sqlParaser = new HashMap<String, Object>();
            Delete statement1 = (Delete) statement;
            sqlParaser.put(TABLES,SqlParserUtil.getTableList(statement));
            parserJoins(statement1.getJoins(),sqlParaser);
            parserWhere(statement1.getWhere(),sqlParaser);
            return sqlParaser;
        }
    }
    static class PDrop extends Drop implements ParserSql{
        @Override
        public Map<String, Object> parser(Statement statement) {
            HashMap<String, Object> sqlParaser = new HashMap<>();
            Drop statement1 = (Drop) statement;
            sqlParaser.put(TABLES,SqlParserUtil.getTableList(statement));
            try{
                String type = statement1.getType();
                sqlParaser.put(TYPE,type);
                sqlParaser.put(WHERE,statement1.getParameters());
            }catch (Exception e){}
            return sqlParaser;
        }
    }
    static class PExecute extends Exception implements ParserSql{
        @Override
        public Map<String, Object> parser(Statement statement) {
            HashMap<String, Object> sqlParaser = new HashMap<>();
            return sqlParaser;
        }
    }
    static class PInsert extends Insert implements ParserSql{
        @Override
        public Map<String, Object> parser(Statement statement) {
            HashMap<String, Object> sqlParaser = new HashMap<>();
            Insert statement1 = (Insert) statement;
            sqlParaser.put(TABLES,SqlParserUtil.getTableList(statement));
            parserColumns(statement1.getColumns(),sqlParaser);
            if (SqlParserUtil.isNotEmpty(((Insert) statement).getSelect()) && SqlParserUtil.isSelect(((Insert) statement).getSelect().getSelectBody())){
                parserSelect(statement1.getSelect().getSelectBody(),sqlParaser);
            }
            return sqlParaser;
        }
    }
    static class PMerge extends Merge implements ParserSql{
        @Override
        public Map<String, Object> parser(Statement statement) {
            HashMap<String, Object> sqlParaser = new HashMap<>();
            sqlParaser.put(TABLES,SqlParserUtil.getTableList(statement));
            return sqlParaser;
        }
    }
    static class PReplace extends Replace implements ParserSql{
        @Override
        public Map<String, Object> parser(Statement statement) {
            HashMap<String, Object> sqlParaser = new HashMap<>();
            sqlParaser.put(TABLES,SqlParserUtil.getTableList(statement));
            return sqlParaser;
        }
    }
    static class PSelect extends Select implements ParserSql{
        @Override
        public Map<String, Object> parser(Statement statement) {
            HashMap<String, Object> sqlParaser = new HashMap<>();
            Select statement1 = (Select) statement;
            parserSelect(statement1.getSelectBody(),sqlParaser);
            return sqlParaser;
        }
    }static class PTruncate extends Truncate implements ParserSql{
        @Override
        public Map<String, Object> parser(Statement statement) {
            HashMap<String, Object> sqlParaser = new HashMap<>();
            sqlParaser.put(TABLES,SqlParserUtil.getTableList(statement));
            return sqlParaser;
        }
    }static class PUpdate extends Update implements ParserSql{
        @Override
        public Map<String, Object> parser(Statement statement) {
            HashMap<String, Object> sqlParaser = new HashMap<>();
            Update statement1 = (Update) statement;
            sqlParaser.put(TABLES,SqlParserUtil.getTableList(statement));
            parserColumns(statement1.getColumns(),sqlParaser);
            parserJoins(statement1.getJoins(),sqlParaser);
            parserWhere(statement1.getWhere(),sqlParaser);
            if(SqlParserUtil.isNotEmpty(statement1.getSelect()))parserSelect(statement1.getSelect().getSelectBody(),sqlParaser);
            return sqlParaser;
        }
    }
    static class PUpsert extends Upsert implements ParserSql{
        @Override
        public Map<String, Object> parser(Statement statement) {
            HashMap<String, Object> sqlParaser = new HashMap<>();
            sqlParaser.put(TABLES,SqlParserUtil.getTableList(statement));
            return sqlParaser;
        }
    }
    static class POther implements ParserSql{
        @Override
        public Map<String, Object> parser(Statement statement) {
            HashMap<String, Object> sqlParaser = new HashMap<>();
            return sqlParaser;
        }
    }

    private static synchronized void parserJoins(SelectBody selectBody,HashMap<String, Object> map){
        // 获取关联表查询
        List<Join> joins;
        while (SqlParserUtil.isNotEmpty(joins = SqlParserUtil.getSelectJoins(selectBody))){
            for (int i = 0; i < joins.size(); i++) {
                Join data = joins.get(i);
                if(data.getRightItem() instanceof SubSelect){
                    SelectBody subBody = ((SubSelect) data.getRightItem()).getSelectBody();
                    HashMap<String, Object> mapBak = new HashMap<String, Object>();
                    parserSelect(subBody,mapBak);
                    map.put("JOIN"+(i+1),mapBak);
                } else {
                    String name = ((Table) data.getRightItem()).getName();
                    map.put("JOIN"+(i+1),name);
                }
                //获取ON
                Expression onExpression = data.getOnExpression();
                if (SqlParserUtil.isNotEmpty(onExpression)){
                    map.put(ON,onExpression.getASTNode().jjtGetValue().toString());
                }
            }
            break;
        }
    }

    private static synchronized void parserJoins(List<Join> join,HashMap<String, Object> map){
        // 获取关联表查询
        List<Join> joins;
        while (SqlParserUtil.isNotEmpty(joins = join)){
            for (int i = 0; i < joins.size(); i++) {
                Join data = joins.get(i);
                if(data.getRightItem() instanceof SubSelect){
                    SelectBody subBody = ((SubSelect) data.getRightItem()).getSelectBody();
                    HashMap<String, Object> mapBak = new HashMap<String, Object>();
                    parserSelect(subBody,mapBak);
                    map.put("JOIN"+(i+1),mapBak);
                } else {
                    String name = ((Table) data.getRightItem()).getName();
                    map.put("JOIN"+(i+1),name);
                }
                //获取ON
                Expression onExpression = data.getOnExpression();
                if (SqlParserUtil.isNotEmpty(onExpression)){
                    map.put(ON,onExpression.getASTNode().jjtGetValue().toString());
                }
            }
            break;
        }
    }

    /**
     * 存在Where进行分析Where
     * @param selectBody
     * @param map
     */
    private static synchronized void parserWhere(SelectBody selectBody,HashMap<String, Object> map){
        //获取Where条件
        Expression where = SqlParserUtil.getSelectWhere(selectBody);
        if (SqlParserUtil.isNotEmpty(where)){
            ArrayList<String> whereList = new ArrayList<>();
            SqlParserUtil.getParserWhere(where,whereList);
            map.put(WHERE,whereList);
        }
    }

    /**
     * 存在Where进行分析Where
     * @param where
     * @param map
     */
    private static synchronized void parserWhere(Expression where,HashMap<String, Object> map){
        if (SqlParserUtil.isNotEmpty(where)){
            ArrayList<String> whereList = new ArrayList<>();
            SqlParserUtil.getParserWhere(where,whereList);
            map.put(WHERE,whereList);
        }
    }

    private static synchronized void parserColumns(List<Column> columns,HashMap<String, Object> map){
        if (SqlParserUtil.isNotEmpty(columns)){
            ArrayList<String> list = new ArrayList<>();
            for (Column column: columns) {
                list.add(column.getColumnName());
            }
           map.put(COLUMNS,list);
        }
    }

    /**
     * 解析 select 语句
     */
    private static synchronized void parserSelect(SelectBody selectBody, HashMap<String, Object> map){
        //获取表名
        List<String> tableList = SqlParserUtil.getTableList(selectBody.toString());
        if (SqlParserUtil.isNotEmpty(tableList))map.put(TABLES,tableList);
        //获取字段
        List<SelectItem> selectItems = SqlParserUtil.getSelectItems(selectBody);
        if(SqlParserUtil.isNotEmpty(selectItems)){
            ArrayList<String> Columns = new ArrayList<String>();
            for (SelectItem data: selectItems) {
                Columns.add(data.toString());
            }
            map.put(COLUMNS,Columns);
        }

        parserJoins(selectBody,map);
        // 获取当前是否存在子查询
        SelectBody selectBody1 = selectBody;
        while ((selectBody1 = SqlParserUtil.getSeedSelectBody(selectBody1)) != null){
            HashMap<String, Object> mapBak = new HashMap<String, Object>();
            parserSelect(selectBody1,mapBak);
            map.put(SEED,mapBak);
        }
        parserWhere(selectBody,map);
    }
}
