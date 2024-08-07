package com.ssj.gis4.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author
 */
public class ListStringTypeHandler extends BaseTypeHandler<List<String>> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, String.join(",", parameter));
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        if (rs.getString(columnName) == null) {
            return new ArrayList<>();
        }
        return Arrays.asList(rs.getString(columnName).split(","));
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        if (rs.getString(columnIndex) == null) {
            return new ArrayList<>();
        }
        return Arrays.asList(rs.getString(columnIndex).split(","));
    }

    @Override
    public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        if (cs.getString(columnIndex) == null) {
            return new ArrayList<>();
        }
        return Arrays.asList(cs.getString(columnIndex).split(","));
    }
}
