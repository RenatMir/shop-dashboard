package com.shopdashboardservice.repository;

import com.shopdashboardservice.model.ProductToOrder;
import com.shopdashboardservice.model.listfilters.ProductToOrderListFilter;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import static com.shopdashboardservice.model.listfilters.ProductToOrderListFilter.FILTER_FIELDS.limit;
import static com.shopdashboardservice.model.listfilters.ProductToOrderListFilter.FILTER_FIELDS.offset;
import static com.shopdashboardservice.model.listfilters.ProductToOrderListFilter.FILTER_FIELDS.price;
import static com.shopdashboardservice.model.listfilters.ProductToOrderListFilter.FILTER_FIELDS.productName;
import static com.shopdashboardservice.utils.JdbcUtils.getTimestampOrNull;
import static com.shopdashboardservice.utils.JdbcUtils.sqlParameterSourceExtractor;
import static java.lang.String.format;

@Slf4j
@Repository
public class ProductToOrderRepository extends BaseRepository<ProductToOrder> {

    private static final String SQL_SELECT_PRODUCTS_TO_ORDER = "SELECT * FROM shop_dashboard.products_to_order WHERE 1=1";

    private static final String SQL_COUNT_PRODUCTS_TO_ORDER = "SELECT count(*) FROM shop_dashboard.products_to_order WHERE 1=1";

    private static final String SQL_INSERT_PRODUCT_TO_ORDER =
            "INSERT INTO shop_dashboard.products_to_order (product_name, price) VALUES (:productName, :price) RETURNING version, last_change_date;";

    private static final String SQL_UPDATE_PRODUCT_TO_ORDER =
            "UPDATE shop_dashboard.products_to_order SET price=:price WHERE product_name=:productName RETURNING version, last_change_date;";

    private static final String SQL_DELETE_PRODUCT_TO_ORDER =
            "DELETE FROM shop_dashboard.products_to_order WHERE product_name=:productName";

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ProductToOrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    public List<ProductToOrder> getProductsToOrderByFilter(ProductToOrderListFilter filter) {
        return namedParameterJdbcTemplate.query(
                createSelectQueryByFilter(filter, false),
                createSqlParameterSourceByFilter(filter),
                rowMapper
        );
    }

    public Integer getTotalRowCount(ProductToOrderListFilter filter) {
        return namedParameterJdbcTemplate.queryForObject(
                createSelectQueryByFilter(filter, true),
                createSqlParameterSourceByFilter(filter),
                Integer.class
        );
    }

    public ProductToOrder getProductToOrderByProductName(String productName) {
        List<ProductToOrder> products = getProductsToOrderByFilter(new ProductToOrderListFilter().setProductName(productName));

        return products.isEmpty()
                ? null
                : products.get(0);
    }

    public ProductToOrder addProductToOrder(ProductToOrder product) {
        String query = SQL_INSERT_PRODUCT_TO_ORDER;
        SqlParameterSource parameterSource = createSqlParameterSource(product);

        Map<String, Object> insertResult = namedParameterJdbcTemplate.query(
                query,
                parameterSource,
                this::extractUpdateResult
        );
        log.info("Executing query ({}) with parameters: {}", query, sqlParameterSourceExtractor(parameterSource));

        handleOptimisticLock(product, insertResult);
        return product;
    }

    public ProductToOrder updateProductToOrder(ProductToOrder product) {
        String query = SQL_UPDATE_PRODUCT_TO_ORDER;
        SqlParameterSource parameterSource = createSqlParameterSource(product);

        Map<String, Object> updateResult = namedParameterJdbcTemplate.query(
                query,
                parameterSource,
                this::extractUpdateResult
        );
        log.info("Executing query ({}) with parameters: {}", query, sqlParameterSourceExtractor(parameterSource));

        handleOptimisticLock(product, updateResult);
        return product;
    }

    public void deleteProductToOrder(String productName) {
        String query = SQL_DELETE_PRODUCT_TO_ORDER;
        SqlParameterSource parameterSource = createSqlParameterSource(new ProductToOrder().setProductName(productName));

        namedParameterJdbcTemplate.update(
                query,
                parameterSource
        );
        log.info("Executing query ({}) with parameters: {}", query, sqlParameterSourceExtractor(parameterSource));
    }

    private SqlParameterSource createSqlParameterSourceByFilter(ProductToOrderListFilter filter) {
        return new MapSqlParameterSource()
                .addValue(productName.name(), filter.getProductName() + '%')
                .addValue(price.name(), filter.getPrice())
                .addValue(offset.name(), filter.getPageNumber() * filter.getPageSize())
                .addValue(limit.name(), filter.getPageSize());
    }

    private SqlParameterSource createSqlParameterSource(ProductToOrder product) {
        return new MapSqlParameterSource()
                .addValue(productName.name(), product.getProductName())
                .addValue(price.name(), product.getPrice());
    }

    private String createSelectQueryByFilter(ProductToOrderListFilter filter, boolean countSelect) {
        StringBuilder query = new StringBuilder(countSelect
                ? SQL_COUNT_PRODUCTS_TO_ORDER
                : SQL_SELECT_PRODUCTS_TO_ORDER);

        if (StringUtils.isNotBlank(filter.getProductName())) {
            query.append(format(" AND product_name ILIKE (:%s)", productName));
        }

        if (!countSelect) {
            query.append(filter.getOrderBySql());
            if (filter.isPageable()) {
                query.append(format(" OFFSET (:%s) LIMIT (:%s)", offset, limit));
            }
        }

        log.info("Executing query ({})", query);

        return query.toString();
    }

    private final RowMapper<ProductToOrder> rowMapper = (rs, rowNum) ->  {
        ProductToOrder product = new ProductToOrder();

        product.setId(rs.getLong("id"));
        product.setProductName(rs.getString("product_name"));
        product.setPrice(rs.getDouble("price"));
        product.setLastChangeDate(getTimestampOrNull(rs, "last_change_date"));
        product.setVersion(rs.getInt("version"));

        return product;
    };
    @Override
    protected String getEntityName() {
        return "products_to_order";
    }

    @Override
    protected String getCode(ProductToOrder object) {
        if (object == null) {
            return null;
        }
        return String.valueOf(object.getProductName());
    }
}
