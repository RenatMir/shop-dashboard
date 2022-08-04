package com.shopdashboardservice.utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Collection;
import java.util.TimeZone;

public class JdbcUtils {

    private static final Calendar tzUTC = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

    private JdbcUtils() {
    }

    public static void setIntOrNull(final PreparedStatement ps, final Integer v, final int i) throws SQLException {
        if (v == null) {
            ps.setNull(i, java.sql.Types.NULL);
        } else {
            ps.setInt(i, v);
        }
    }

    public static void setLongOrNull(final PreparedStatement ps, final Long v, final int i) throws SQLException {
        if (v == null) {
            ps.setNull(i, java.sql.Types.NULL);
        } else {
            ps.setLong(i, v);
        }
    }

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

    public static void setTimestampOrNull(final PreparedStatement ps, final Instant v, final int i) throws SQLException {
        if (v == null) {
            ps.setNull(i, java.sql.Types.NULL);
        } else {
            Timestamp ts = Timestamp.from(v);
            ps.setTimestamp(i, ts, tzUTC);
        }
    }

    public static String createInString(Collection<?> inParameters) {
        if (inParameters == null || inParameters.isEmpty())
            return null;
        StringBuilder b = new StringBuilder();
        b.append("?,".repeat(inParameters.size()));
        b.delete(b.length() - 1, b.length());

        return b.toString();

    }
}
