package com.example.lixc.enums;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class BaseEnumTypeHandler<E extends Enum<E>> extends BaseTypeHandler<E> {
    private Class<E> type;
    private Map<Integer, E> map = new HashMap<>();

    public BaseEnumTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
        E[] enums = type.getEnumConstants();
        if (enums == null) {
            throw new IllegalArgumentException(type.getSimpleName() + " does not represent an enum type.");
        }
        for (E e : enums) {
            BaseEnum valuedEnum = (BaseEnum) e;
            map.put(valuedEnum.getCode(), e);
        }
    }

    @Override
    public void setNonNullParameter(java.sql.PreparedStatement ps, int i, E parameter, JdbcType jdbcType)
            throws SQLException {
        BaseEnum valuedEnum = (BaseEnum) parameter;
        ps.setInt(i, valuedEnum.getCode());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int i = rs.getInt(columnName);
        if (rs.wasNull()) {
            return null;
        } else {
            return getBaseEnum(i);
        }
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int i = rs.getInt(columnIndex);
        if (rs.wasNull()) {
            return null;
        } else {
            return getBaseEnum(i);
        }
    }

    @Override
    public E getNullableResult(java.sql.CallableStatement cs, int columnIndex) throws SQLException {
        int i = cs.getInt(columnIndex);
        if (cs.wasNull()) {
            return null;
        } else {
            return getBaseEnum(i);
        }
    }

    private E getBaseEnum(int value) {
        try {
            return map.get(value);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Cannot convert " + value + " to " + type.getSimpleName() + " by value.", ex);
        }
    }
}
