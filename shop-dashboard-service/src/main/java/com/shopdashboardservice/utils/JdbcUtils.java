package com.shopdashboardservice.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.TimeZone;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class JdbcUtils {

    private static final Calendar tzUTC = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

    public static Integer getIntOrNull(final ResultSet rs, final String columnName) throws SQLException {
        int r = rs.getInt(columnName);
        if (rs.wasNull())
            return null;

        return r;
    }

    public static Instant getTimestampOrNull(final ResultSet rs, final String columnName) throws SQLException {
        Timestamp r = rs.getTimestamp(columnName, tzUTC);
        if (rs.wasNull())
            return null;

        return r.toInstant();
    }

    public static String sqlParameterSourceExtractor(SqlParameterSource parameterSource){
        String[] parameterNames = parameterSource.getParameterNames();

        if (parameterNames == null){
            return "";
        }

        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        for (String paramName : parameterNames){
            map.put(paramName, parameterSource.getValue(paramName));
        }

        return map.toString();
    }
}
