package com.shopdashboardservice.repository;

import com.shopdashboardservice.model.Order;
import com.shopdashboardservice.model.listfilters.OrderListFilter;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import static com.shopdashboardservice.model.listfilters.OrderListFilter.FILTER_FIELDS.clientName;
import static com.shopdashboardservice.model.listfilters.OrderListFilter.FILTER_FIELDS.uuid;
import static com.shopdashboardservice.model.listfilters.OrderListFilter.FILTER_FIELDS.limit;
import static com.shopdashboardservice.model.listfilters.OrderListFilter.FILTER_FIELDS.offset;
import static com.shopdashboardservice.model.listfilters.OrderListFilter.FILTER_FIELDS.orderDate;
import static com.shopdashboardservice.utils.JdbcUtils.getTimestampOrNull;
import static java.lang.String.format;

@Repository
public class OrderRepository extends BaseRepository<Order> {

    private static final String SQL_SELECT_ORDERS = "SELECT * FROM shop_dashboard.orders WHERE 1=1";

    private static final String SQL_COUNT_ORDERS = "SELECT count(*) FROM shop_dashboard.orders WHERE 1=1";

    private static final String SQL_INSERT_ORDER =
            "INSERT INTO shop_dashboard.orders (client_name, order_amount, config) VALUES (?, ?, (?::json)) RETURNING version, last_change_date;";

    private static final String SQL_UPDATE_ORDER =
            "UPDATE shop_dashboard.orders SET client_name=?, order_amount=?, config=(?::json) WHERE uuid=? RETURNING version, last_change_date;";

    private static final String SQL_DELETE_ORDER =
            "DELETE FROM shop_dashboard.orders WHERE uuid=?";

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public OrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    public List<Order> getOrdersByFilter(OrderListFilter filter) {
        return namedParameterJdbcTemplate.query(
                createSelectQueryByFilter(filter, false),
                createSqlParameterSourceByFilter(filter),
                rowMapper
        );
    }

    public Integer getTotalRowCount(OrderListFilter filter) {
        return namedParameterJdbcTemplate.queryForObject(
                createSelectQueryByFilter(filter, true),
                createSqlParameterSourceByFilter(filter),
                Integer.class
        );
    }

    public Order getOrderByUUID(UUID uuid) {
        List<Order> orders = getOrdersByFilter(new OrderListFilter().setUuid(uuid));

        return orders.isEmpty()
                ? null
                : orders.get(0);
    }

    public Order addOrder(Order order) {
        Map<String, Object> insertResult = jdbcTemplate.query(con -> {
            PreparedStatement ps = con.prepareStatement(SQL_INSERT_ORDER);
            ps.setString(1, order.getClientName());
            ps.setDouble(2, order.getOrderAmount());
            ps.setString(3, order.getConfig());

            return ps;
        }, this::extractUpdateResult);
        handleOptimisticLock(order, insertResult);
        return order;
    }

    public Order updateOrder(Order order) {
        Map<String, Object> updateResult = jdbcTemplate.query(con -> {
            PreparedStatement ps = con.prepareStatement(SQL_UPDATE_ORDER);
            ps.setString(1, order.getClientName());
            ps.setDouble(2, order.getOrderAmount());
            ps.setString(3, order.getConfig());
            ps.setObject(4, order.getUuid());

            return ps;
        }, this::extractUpdateResult);
        handleOptimisticLock(order, updateResult);
        return order;
    }

    public void deleteOrder(UUID uuid) {
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(SQL_DELETE_ORDER);
            ps.setObject(1, uuid);
            return ps;
        });
    }

    private String createSelectQueryByFilter(OrderListFilter filter, boolean countSelect) {
        StringBuilder query = new StringBuilder(countSelect
                ? SQL_COUNT_ORDERS
                : SQL_SELECT_ORDERS);

        if (filter.getUuid() != null) {
            query.append(format(" AND uuid=(:%s)", uuid));
        }

        if (StringUtils.isNotBlank(filter.getClientName())) {
            query.append(format(" AND client_name ILIKE (:%s)", clientName));
        }

        if (filter.getOrderDate() != null) {
            query.append(format(" AND order_date=(:%s)", orderDate));
        }

        if (!countSelect) {
            query.append(filter.getOrderBySql());
            if (filter.isPageable()) {
                query.append(format(" OFFSET (:%s) LIMIT (:%s)", offset, limit));
            }
        }

        return query.toString();
    }

    private SqlParameterSource createSqlParameterSourceByFilter(OrderListFilter filter) {
        return new MapSqlParameterSource()
                .addValue(uuid.name(), filter.getUuid())
                .addValue(clientName.name(), filter.getClientName() + '%')
                .addValue(orderDate.name(), filter.getOrderDate())
                .addValue(offset.name(), filter.getPageNumber() * filter.getPageSize())
                .addValue(limit.name(), filter.getPageSize());
    }

    private final RowMapper<Order> rowMapper = (rs, rowNum) ->  {
        Order order = new Order();

        order.setUuid(rs.getObject("uuid", UUID.class));
        order.setClientName(rs.getString("client_name"));
        order.setOrderAmount(rs.getDouble("order_amount"));
        order.setOrderDate(rs.getObject("order_date", Timestamp.class));
        order.setConfig(rs.getString("config"));
        order.setLastChangeDate(getTimestampOrNull(rs, "last_change_date"));
        order.setVersion(rs.getInt("version"));

        return order;
    };

    @Override
    protected String getEntityName() {
        return "orders";
    }

    @Override
    protected String getCode(Order object) {
        if (object == null) {
            return null;
        }
        return String.valueOf(object.getUuid());
    }
}
