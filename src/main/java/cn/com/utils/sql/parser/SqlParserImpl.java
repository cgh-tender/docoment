package cn.com.utils.sql.parser;

import cn.com.utils.sql.ParserSql;
import cn.com.utils.sql.SqlParserUtil;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.ComparisonOperator;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.alter.Alter;
import net.sf.jsqlparser.statement.create.index.CreateIndex;
import net.sf.jsqlparser.statement.create.table.CreateTable;
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
    private final static String WHERE = "WHERE";
    private final static String ORDER = "ORDER";
    private final static String SEED = "SEED";
    private final static String ON = "ON";
    private final static String TYPE = "TYPE";

    static class PAlter extends Alter implements ParserSql {
        @Override
        public Map<String, Object> parser(Statement statement) {
            HashMap<String, Object> sqlParaser = new HashMap<>();
            Alter parserSql1 = (Alter) statement;
            try{
                Table table = parserSql1.getTable();
                sqlParaser.put(TABLES,SqlParserUtil.getTableList(statement));
                sqlParaser.put(COLUMNS,table.getFullyQualifiedName());
            }catch (Exception e){ }
            return sqlParaser;
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
            HashMap<String, Object> sqlParaser = new HashMap<>();
            CreateTable parserSql1 = (CreateTable) statement;
            sqlParaser.put(TABLES, SqlParserUtil.getTableList(statement));
            try{
                if (null != parserSql1.getSelect()){
                    SelectBody selectBody = parserSql1.getSelect().getSelectBody();
                    parserSelect(selectBody,sqlParaser);
                }else {
                    sqlParaser.put(COLUMNS,parserSql1.getColumnDefinitions().toString());
                }
            }catch (Exception e){}
            return sqlParaser;
        }
    }

    static class PCreateView extends CreateView implements ParserSql{
        @Override
        public Map<String, Object> parser(Statement statement) {
            HashMap<String, Object> sqlParaser = new HashMap<>();
            sqlParaser.put(TABLES,SqlParserUtil.getTableList(statement));
            return sqlParaser;
        }
    }
    static class PDelete extends Delete implements ParserSql{
        @Override
        public Map<String, Object> parser(Statement statement) {
            HashMap<String, Object> sqlParaser = new HashMap<>();
            sqlParaser.put(TABLES,SqlParserUtil.getTableList(statement));
            return sqlParaser;
        }
    }
    static class PDrop extends Drop implements ParserSql{
        @Override
        public Map<String, Object> parser(Statement statement) {
            HashMap<String, Object> sqlParaser = new HashMap<>();
            Drop parserSql1 = (Drop) statement;
            sqlParaser.put(TABLES,SqlParserUtil.getTableList(statement));
            try{
                String type = parserSql1.getType();
                sqlParaser.put(TYPE,type);
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
            ArrayList<String> columns = new ArrayList<>();
            if (SqlParserUtil.isNotEmpty(statement1.getColumns())){
                for (Column column: statement1.getColumns()) {
                    columns.add(column.getColumnName());
                }
                sqlParaser.put(COLUMNS,columns);
            }
            if (SqlParserUtil.isNotEmpty(((Insert) statement).getSelect()) && SqlParserUtil.isSelect(((Insert) statement).getSelect().getSelectBody())){
                parserSelect(((Insert) statement).getSelect().getSelectBody(),sqlParaser);
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
            Select parserSql1 = (Select) statement;
            parserSelect(parserSql1.getSelectBody(),sqlParaser);
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
            sqlParaser.put(TABLES,SqlParserUtil.getTableList(statement));
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

    //解析 select 语句
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

        // 获取关联表查询
        List<Join> joins;
        while (SqlParserUtil.isNotEmpty(joins = SqlParserUtil.getJoins(selectBody))){
            for (int i = 0; i < joins.size(); i++) {
                Join join = joins.get(i);
                if(join.getRightItem() instanceof SubSelect){
                    SelectBody subBody = ((SubSelect) join.getRightItem()).getSelectBody();
                    HashMap<String, Object> mapBak = new HashMap<String, Object>();
                    parserSelect(subBody,mapBak);
                    map.put("JOIN"+(i+1),mapBak);
                } else {
                    String name = ((Table) join.getRightItem()).getName();
                    map.put("JOIN"+(i+1),name);
                }
                //获取ON
                Expression onExpression = join.getOnExpression();
                if (SqlParserUtil.isNotEmpty(onExpression)){
                    map.put(ON,onExpression.getASTNode().jjtGetValue().toString());
                }
            }
            break;
        }

        // 获取当前是否存在子查询
        SelectBody selectBody1 = selectBody;
        while ((selectBody1 = SqlParserUtil.getSeedSelectBody(selectBody1)) != null){
            HashMap<String, Object> mapBak = new HashMap<String, Object>();
            parserSelect(selectBody1,mapBak);
            map.put(SEED,mapBak);
        }

        //获取Where条件
        Expression where = SqlParserUtil.getWhere(selectBody);
        if (SqlParserUtil.isNotEmpty(where)){
            ArrayList<String> whereList = new ArrayList<>();
            SqlParserUtil.getParserWhere(where,whereList);
            map.put(WHERE,whereList);
        }
    }
}
