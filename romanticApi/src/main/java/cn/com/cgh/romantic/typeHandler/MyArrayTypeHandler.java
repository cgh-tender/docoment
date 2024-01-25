package cn.com.cgh.romantic.typeHandler;

import org.apache.ibatis.type.ArrayTypeHandler;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MyArrayTypeHandler extends ArrayTypeHandler {
    public MyArrayTypeHandler() {
        super();
    }

    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Array array = null;
        try {
            array = rs.getArray(columnName);
        } catch (Exception e) {
        }
        return extractArray(array);
    }

    @Override
    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Array array = null;
        try {
            array = rs.getArray(columnIndex);
        } catch (Exception e) {
        }
        return extractArray(array);
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Array array = null;
        try {
            array = cs.getArray(columnIndex);
        } catch (Exception e) {
        }
        return extractArray(array);
    }
}
