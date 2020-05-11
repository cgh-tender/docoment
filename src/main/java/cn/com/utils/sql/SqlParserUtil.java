package cn.com.utils.sql;

import com.alibaba.fastjson.JSONObject;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.*;
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
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.statement.truncate.Truncate;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.upsert.Upsert;
import net.sf.jsqlparser.util.TablesNamesFinder;

import java.io.*;
import java.util.*;

@SuppressWarnings("ALL")
public class SqlParserUtil {

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
        ArrayList<String> data = new ArrayList<>();
        if (statement instanceof Alter) {
            Alter statement1 = (Alter) statement;
            data.add(statement1.getTable().getName());
            return data;
        } else if (statement instanceof CreateIndex) {
            CreateIndex statement1 = (CreateIndex) statement;
            data.add(statement1.getTable().getName());
            return data;
        } else if (statement instanceof CreateTable) {
            TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
            return tablesNamesFinder.getTableList(statement);
        } else if (statement instanceof CreateView) {
            CreateView statement1 = (CreateView) statement;
            data.add(statement1.getView().getName());
            return data;
        } else if (statement instanceof Delete) {
            TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
            return tablesNamesFinder.getTableList(statement);
        } else if (statement instanceof Drop) {
            Drop statement1 = (Drop) statement;
            data.add(statement1.getName().getName());
        } else if (statement instanceof Execute) {
            TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
            return tablesNamesFinder.getTableList(statement);
        } else if (statement instanceof Insert) {
            TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
            return tablesNamesFinder.getTableList(statement);
        } else if (statement instanceof Merge) {
            TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
            return tablesNamesFinder.getTableList(statement);
        } else if (statement instanceof Replace) {
            TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
            return tablesNamesFinder.getTableList(statement);
        } else if (statement instanceof Select) {
            TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
            return tablesNamesFinder.getTableList(statement);
        } else if (statement instanceof Truncate) {
            TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
            return tablesNamesFinder.getTableList(statement);
        } else if (statement instanceof Update) {
            TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
            return tablesNamesFinder.getTableList(statement);
        } else if (statement instanceof Upsert) {
            TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
            return tablesNamesFinder.getTableList(statement);
        } else {
            return null;
        }
        return data;
    }

    /**
     * <p> 获取tables的表名
     * @return List
     * @param statement
     */
    public static List<String> getTableList(String sql){
        try {
            final Statement sqlStmt = getStatement(sql);
            return getTableList(sqlStmt);
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
        return new ArrayList<String>();
    }


    public static Expression getSelectWhere(SelectBody selectBody){
        if (selectBody instanceof PlainSelect) {
            Expression where = ((PlainSelect) selectBody).getWhere();
            return where;
        }
        return null;
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
        return null;
    }

    /**
     * <p> 获取limit值
     * @param selectBody
     * @return Limit
     */
    public static Limit getSelectLimit(SelectBody selectBody){
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
     * 是否还有子查询
     * @param selectBody
     * @return
     */
    public static boolean isSeedSelect(SelectBody selectBody){
        if(selectBody instanceof PlainSelect){
            FromItem fromItem = ((PlainSelect) selectBody).getFromItem();
            if(fromItem instanceof SubSelect){
                return true;
            }
        }
        return false;
    }

    public static SelectBody getSeedSelectBody(SelectBody selectBody){
        if (isSeedSelect(selectBody)){
           return  ((SubSelect) ((PlainSelect) selectBody).getFromItem()).getSelectBody();
        }
        return null;
    }

    public static boolean isSelect(SelectBody selectBody){
        if (selectBody instanceof PlainSelect) {
            return true;
        }
        return false;
    }
    /**
     * <p> 获取join层级
     * @param selectBody
     * @return List<Join>
     */
    public static List<Join> getSelectJoins(SelectBody selectBody) {
        if(isSelect(selectBody)){
            List<Join> joins = ((PlainSelect) selectBody).getJoins();
            return joins;
        }
        return null;
    }

    public static String ObjtoString(Object o){
        return JSONObject.toJSONString(o);
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

    public static void getParserWhere(Expression expression , List<String> wheres){
        try{
            Expression rightExpression = ((AndExpression) expression).getRightExpression();
            Expression leftExpression = ((AndExpression) expression).getLeftExpression();
            wheres.add(rightExpression.toString());
            getParserWhere(leftExpression,wheres);
        }catch (Exception e){
            wheres.add(expression.toString());
        }
    }

    /**
     * 获取文件中的全部sql进行格式化后放到桶中
     * @param file
     * @return
     */
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
                if (isNotEmpty(tem)) {
                    sql.append(" ");
                    sql.append(tem);
                }
                if (tem.contains(";")) {
                    sqls.add(sql.toString());
                    sql = new StringBuffer();
                }
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

    /**
     * 对单sql 进行格式化
     * @param sql
     * @return
     */
    public static String getSql(String sql) {
        if (isEmpty(sql)) return null;
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
                if (isNotEmpty(tem)) {
                    data.append(" ");
                    data.append(tem);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data.toString();
    }
    /**
     * 对象是否为空
     * @param o String,List,Map,Object[],int[],long[]
     * @return
     */

    public static boolean isEmpty(Object o) {
        if (o == null) {
            return true;
        }
        if (o instanceof String) {
            if (o.toString().trim().equals("")||o.toString().trim().equals("null")) {
                return true;
            }
        } else if (o instanceof List) {
            if (((List) o).size() == 0) {
                return true;
            }
        } else if (o instanceof Map) {
            if (((Map) o).size() == 0) {
                return true;
            }
        } else if (o instanceof Set) {
            if (((Set) o).size() == 0) {
                return true;
            }
        } else if (o instanceof Object[]) {
            if (((Object[]) o).length == 0) {
                return true;
            }
        } else if (o instanceof int[]) {
            if (((int[]) o).length == 0) {
                return true;
            }
        } else if (o instanceof long[]) {
            if (((long[]) o).length == 0) {
                return true;
            }
        } else if(o instanceof Integer) {
            if (o.toString().trim().equals("0")) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNotEmpty(Object o){
        return !isEmpty(o);
    }

}
