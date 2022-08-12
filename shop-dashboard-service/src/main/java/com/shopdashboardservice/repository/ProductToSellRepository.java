package com.shopdashboardservice.repository;

import com.shopdashboardservice.model.ProductToSell;
import com.shopdashboardservice.model.listfilters.ProductToSellListFilter;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import static com.shopdashboardservice.model.listfilters.ProductToSellListFilter.FILTER_FIELDS.limit;
import static com.shopdashboardservice.model.listfilters.ProductToSellListFilter.FILTER_FIELDS.offset;
import static com.shopdashboardservice.model.listfilters.ProductToSellListFilter.FILTER_FIELDS.price;
import static com.shopdashboardservice.model.listfilters.ProductToSellListFilter.FILTER_FIELDS.productName;
import static com.shopdashboardservice.utils.JdbcUtils.getTimestampOrNull;
import static java.lang.String.format;

@Repository
public class ProductToSellRepository extends BaseRepository<ProductToSell> {

    private static final String SQL_SELECT_PRODUCTS_TO_SELL = "SELECT * FROM shop_dashboard.products_to_sell WHERE 1=1";

    private static final String SQL_COUNT_PRODUCTS_TO_SELL = "SELECT count(*) FROM shop_dashboard.products_to_sell WHERE 1=1";

    private static final String SQL_INSERT_PRODUCT_TO_SELL =
            "INSERT INTO shop_dashboard.products_to_sell (product_name, price) VALUES (:productName, :price) RETURNING version, last_change_date;";

    private static final String SQL_UPDATE_PRODUCT_TO_SELL =
            "UPDATE shop_dashboard.products_to_sell SET price=:price WHERE product_name=:productName RETURNING version, last_change_date;";

    private static final String SQL_DELETE_PRODUCT_TO_SELL =
            "DELETE FROM shop_dashboard.products_to_sell WHERE product_name=:productName";

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ProductToSellRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    public List<ProductToSell> getProductsToSellByFilter(ProductToSellListFilter filter) {
        return namedParameterJdbcTemplate.query(
                createSelectQueryByFilter(filter, false),
                createSqlParameterSourceByFilter(filter),
                rowMapper
        );
    }

    public Integer getTotalRowCount(ProductToSellListFilter filter) {
        return namedParameterJdbcTemplate.queryForObject(
                createSelectQueryByFilter(filter, true),
                createSqlParameterSourceByFilter(filter),
                Integer.class
        );
    }

    public ProductToSell getProductToSellByProductName(String productName) {
        List<ProductToSell> products = getProductsToSellByFilter(new ProductToSellListFilter().setProductName(productName));

        return products.isEmpty()
                ? null
                : products.get(0);
    }

    public ProductToSell addProductToSell(ProductToSell product) {
        Map<String, Object> insertResult = namedParameterJdbcTemplate.query(
                SQL_INSERT_PRODUCT_TO_SELL,
                createSqlParameterSource(product),
                this::extractUpdateResult
        );
        handleOptimisticLock(product, insertResult);
        return product;
    }

    public ProductToSell updateProductToSell(ProductToSell product) {
        Map<String, Object> updateResult = namedParameterJdbcTemplate.query(
                SQL_UPDATE_PRODUCT_TO_SELL,
                createSqlParameterSource(product),
                this::extractUpdateResult
        );
        handleOptimisticLock(product, updateResult);
        return product;
    }

    public void deleteProductToSell(String productName) {
        namedParameterJdbcTemplate.update(
                SQL_DELETE_PRODUCT_TO_SELL,
                createSqlParameterSource(new ProductToSell().setProductName(productName))
                );
    }

    private SqlParameterSource createSqlParameterSourceByFilter(ProductToSellListFilter filter) {
        return new MapSqlParameterSource()
                .addValue(productName.name(), filter.getProductName() + '%')
                .addValue(price.name(), filter.getPrice())
                .addValue(offset.name(), filter.getPageNumber() * filter.getPageSize())
                .addValue(limit.name(), filter.getPageSize());
    }

    private SqlParameterSource createSqlParameterSource(ProductToSell product) {
        return new MapSqlParameterSource()
                .addValue(productName.name(), product.getProductName())
                .addValue(price.name(), product.getPrice());
    }

    private String createSelectQueryByFilter(ProductToSellListFilter filter, boolean countSelect) {
        StringBuilder query = new StringBuilder(countSelect
                ? SQL_COUNT_PRODUCTS_TO_SELL
                : SQL_SELECT_PRODUCTS_TO_SELL);

        if (StringUtils.isNotBlank(filter.getProductName())) {
            query.append(format(" AND product_name ILIKE (:%s)", productName));
        }

        if (!countSelect) {
            query.append(filter.getOrderBySql());
            if (filter.isPageable()) {
                query.append(format(" OFFSET (:%s) LIMIT (:%s)", offset, limit));
            }
        }

        return query.toString();
    }

    private final RowMapper<ProductToSell> rowMapper = (rs, rowNum) ->  {
        ProductToSell product = new ProductToSell();

        product.setId(rs.getLong("id"));
        product.setProductName(rs.getString("product_name"));
        product.setPrice(rs.getDouble("price"));
        product.setLastChangeDate(getTimestampOrNull(rs, "last_change_date"));
        product.setVersion(rs.getInt("version"));

        return product;
    };
    @Override
    protected String getEntityName() {
        return "products_to_sell";
    }

    @Override
    protected String getCode(ProductToSell object) {
        if (object == null) {
            return null;
        }
        return String.valueOf(object.getProductName());
    }
}
