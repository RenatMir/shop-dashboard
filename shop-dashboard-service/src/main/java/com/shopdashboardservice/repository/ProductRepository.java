package com.shopdashboardservice.repository;

import com.shopdashboardservice.model.Product;
import com.shopdashboardservice.model.listfilters.ProductListFilter;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import static com.shopdashboardservice.model.listfilters.ProductListFilter.FILTER_FIELDS.expirationDays;
import static com.shopdashboardservice.model.listfilters.ProductListFilter.FILTER_FIELDS.id;
import static com.shopdashboardservice.model.listfilters.ProductListFilter.FILTER_FIELDS.limit;
import static com.shopdashboardservice.model.listfilters.ProductListFilter.FILTER_FIELDS.name;
import static com.shopdashboardservice.model.listfilters.ProductListFilter.FILTER_FIELDS.offset;
import static com.shopdashboardservice.model.listfilters.ProductListFilter.FILTER_FIELDS.productType;
import static com.shopdashboardservice.utils.JdbcUtils.getTimestampOrNull;
import static java.lang.String.format;

@Repository
public class ProductRepository extends BaseRepository<Product> {

    private static final String SQL_SELECT_PRODUCTS = "SELECT * FROM shop_dashboard.product WHERE 1=1";

    private static final String SQL_COUNT_PRODUCTS = "SELECT count(*) FROM shop_dashboard.product WHERE 1=1";

    private static final String SQL_INSERT_PRODUCT =
            "INSERT INTO shop_dashboard.product (name, product_type, expiration_days) VALUES (:name, :productType, :expirationDays) RETURNING version, last_change_date;";

    private static final String SQL_UPDATE_PRODUCT =
            "UPDATE shop_dashboard.product SET name=:name, product_type=:productType, expiration_days=:expirationDays WHERE id=:id RETURNING version, last_change_date;";

    private static final String SQL_DELETE_PRODUCT =
            "DELETE FROM shop_dashboard.product WHERE name=:name";

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    public List<Product> getProductsByFilter(ProductListFilter filter) {
        return namedParameterJdbcTemplate.query(
                createSelectQueryByFilter(filter, false),
                createSqlParameterSourceByFilter(filter),
                rowMapper
        );
    }

    public Integer getTotalRowCount(ProductListFilter filter) {
        return namedParameterJdbcTemplate.queryForObject(
                createSelectQueryByFilter(filter, true),
                createSqlParameterSourceByFilter(filter),
                Integer.class
        );
    }

    public Product getProductByName(String name) {
        List<Product> products = getProductsByFilter(new ProductListFilter().setName(name));

        return products.isEmpty()
                ? null
                : products.get(0);
    }

    public Product addProduct(Product product) {
        Map<String, Object> insertResult = namedParameterJdbcTemplate.query(
                SQL_INSERT_PRODUCT,
                createSqlParameterSource(product),
                this::extractUpdateResult
        );
        handleOptimisticLock(product, insertResult);
        return product;
    }

    public Product updateProduct(Product product) {
        Map<String, Object> updateResult = namedParameterJdbcTemplate.query(
                SQL_UPDATE_PRODUCT,
                createSqlParameterSource(product),
                this::extractUpdateResult
        );
        handleOptimisticLock(product, updateResult);
        return product;
    }

    public void deleteProduct(String name) {
        jdbcTemplate.update(
                SQL_DELETE_PRODUCT,
                createSqlParameterSource(new Product().setName(name))
        );
    }

    private SqlParameterSource createSqlParameterSourceByFilter(ProductListFilter filter) {
        return new MapSqlParameterSource()
                .addValue(id.name(), filter.getId())
                .addValue(name.name(), filter.getName() + '%')
                .addValue(productType.name(), filter.getProductType() + '%')
                .addValue(offset.name(), filter.getPageNumber() * filter.getPageSize())
                .addValue(limit.name(), filter.getPageSize());
    }

    private SqlParameterSource createSqlParameterSource(Product product) {
        return new MapSqlParameterSource()
                .addValue(id.name(), product.getId())
                .addValue(name.name(), product.getName())
                .addValue(productType.name(), product.getProductType())
                .addValue(expirationDays.name(), product.getExpirationDays());
    }

    private String createSelectQueryByFilter(ProductListFilter filter, boolean countSelect) {
        StringBuilder query = new StringBuilder(countSelect
                ? SQL_COUNT_PRODUCTS
                : SQL_SELECT_PRODUCTS);

        if (filter.getId() != null) {
            query.append(format(" AND id=(:%s)", id));
        }

        if (StringUtils.isNotBlank(filter.getName())) {
            query.append(format(" AND name ILIKE (:%s)", name));
        }

        if (StringUtils.isNotBlank(filter.getProductType())) {
            query.append(format(" AND product_type ILIKE (:%s)", productType));
        }

        if (!countSelect) {
            query.append(filter.getOrderBySql());
            if (filter.isPageable()) {
                query.append(format(" OFFSET (:%s) LIMIT (:%s)", offset, limit));
            }
        }

        return query.toString();
    }

    private final RowMapper<Product> rowMapper = (rs, rowNum) ->  {
        Product product = new Product();

        product.setId(rs.getLong("id"));
        product.setName(rs.getString("name"));
        product.setProductType(rs.getString("product_type"));
        product.setExpirationDays(rs.getInt("expiration_days"));
        product.setLastChangeDate(getTimestampOrNull(rs, "last_change_date"));
        product.setVersion(rs.getInt("version"));

        return product;
    };

    @Override
    protected String getEntityName() {
        return "products_to_sell";
    }

    @Override
    protected String getCode(Product object) {
        if (object == null) {
            return null;
        }
        return String.valueOf(object.getId());
    }
}