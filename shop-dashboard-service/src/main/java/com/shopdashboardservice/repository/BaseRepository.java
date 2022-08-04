package com.shopdashboardservice.repository;

import com.shopdashboardservice.model.AbstractEntity;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static com.shopdashboardservice.utils.JdbcUtils.getIntOrNull;
import static com.shopdashboardservice.utils.JdbcUtils.getTimestampOrNull;

public abstract class BaseRepository<T extends AbstractEntity> {

    protected final static String UPDATE_RESULT_VERSION = "updateResult#version";
    protected final static String UPDATE_RESULT_LAST_CHANGE_DATA = "updateResult#lastChangeDate";

    protected AbstractEntity handleOptimisticLock(T entity, Map<String, Object> updateResult) {
        if (updateResult == null)
            return null;

//        if (getVersion(updateResult) != entity.getVersion() + 1) {
//            throw new AppException(ErrorCode.OPTIMISTIC_LOCK_FAILED, "Updating %s  with code=%s failed. Optimistic lock failed.",
//                    getEntityName(), getCode(entity));
//        }
        entity.setVersion(getVersion(updateResult));
        return entity.setLastChangeDate(getLastChangeDate(updateResult));
    }

    protected Map<String, Object> extractUpdateResult(final ResultSet rs) throws SQLException {
        if (rs.next()) {
            final Map<String, Object> result = new HashMap<>();

            result.put(UPDATE_RESULT_VERSION, getIntOrNull(rs, "version"));
            result.put(UPDATE_RESULT_LAST_CHANGE_DATA, getTimestampOrNull(rs, "last_change_date"));

            return result;
        }
        return null;
    }

    private int getVersion(Map<String, Object> updateResult) {
        return (int) updateResult.get(UPDATE_RESULT_VERSION);
    }

    private Instant getLastChangeDate(Map<String, Object> updateResult) {
        return (Instant) updateResult.get(UPDATE_RESULT_LAST_CHANGE_DATA);
    }

    protected abstract String getEntityName();

}
