package com.shopdashboardservice.repository;

import com.shopdashboardservice.model.ProductType;
import com.shopdashboardservice.model.listfilters.ProductTypeListFilter;
import com.shopdashboardservice.model.listfilters.ProductTypeListFilter.FILTER_FIELDS;
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

import static com.shopdashboardservice.model.listfilters.ProductTypeListFilter.FILTER_FIELDS.limit;
import static com.shopdashboardservice.model.listfilters.ProductTypeListFilter.FILTER_FIELDS.offset;
import static com.shopdashboardservice.model.listfilters.ProductTypeListFilter.FILTER_FIELDS.type;
import static com.shopdashboardservice.utils.JdbcUtils.getTimestampOrNull;
import static com.shopdashboardservice.utils.JdbcUtils.sqlParameterSourceExtractor;
import static java.lang.String.format;


@Slf4j
@Repository
public class ProductTypeRepository extends BaseRepository<ProductType> {

    private static final String SQL_SELECT_PRODUCT_TYPES =
        "SELECT * FROM shop_dashboard.product_types WHERE 1=1";

    private static final String SQL_COUNT_PRODUCT_TYPES =
        "SELECT count(*) FROM shop_dashboard.product_types WHERE 1=1";

    private static final String SQL_INSERT_PRODUCT_TYPE =
        "INSERT INTO shop_dashboard.product_types (type) VALUES (:type) RETURNING version, last_change_date;";

    private static final String SQL_UPDATE_PRODUCT_TYPE =
        "UPDATE shop_dashboard.product_types SET type=:type WHERE type=:type RETURNING version, last_change_date;";

    private static final String SQL_DELETE_PRODUCT_TYPE =
        "DELETE FROM shop_dashboard.product_types WHERE type=:type";

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public ProductTypeRepository( JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    public List<ProductType> getProductTypesByFilter(ProductTypeListFilter filter){
        return namedParameterJdbcTemplate.query(
                createSelectQueryByFilter(filter, false),
                createSqlParameterSourceByFilter(filter),
                rowMapper
        );
    }

    public Integer getTotalRowCount(ProductTypeListFilter filter) {
        return namedParameterJdbcTemplate.queryForObject(
                createSelectQueryByFilter(filter, true),
                createSqlParameterSourceByFilter(filter),
                Integer.class
        );
    }

    public ProductType getProductTypeById(String type) {
        List<ProductType> productTypes = getProductTypesByFilter(new ProductTypeListFilter().setType(type));

        return productTypes.isEmpty()
                ? null
                : productTypes.get(0);
    }

    public ProductType addProductType(ProductType productType) {
        String query = SQL_INSERT_PRODUCT_TYPE;
        SqlParameterSource parameterSource = createSqlParameterSource(productType);

        Map<String, Object> insertResult = namedParameterJdbcTemplate.query(
                query,
                parameterSource,
                this::extractUpdateResult
        );
        log.info("Executing query {} with parameters: {}", query, sqlParameterSourceExtractor(parameterSource));

        handleOptimisticLock(productType, insertResult);
        return productType;
    }

    public ProductType updateProductType(ProductType productType) {
        String query = SQL_UPDATE_PRODUCT_TYPE;
        SqlParameterSource parameterSource = createSqlParameterSource(productType);

        Map<String, Object> updateResult = namedParameterJdbcTemplate.query(
                query,
                parameterSource,
                this::extractUpdateResult
        );
        log.info("Executing query ({}) with parameters: {}", query, sqlParameterSourceExtractor(parameterSource));

        handleOptimisticLock(productType, updateResult);
        return productType;
    }

    public void deleteProductType(String type) {
        String query = SQL_DELETE_PRODUCT_TYPE;
        SqlParameterSource parameterSource = createSqlParameterSource(new ProductType().setType(type));

        namedParameterJdbcTemplate.update(
                query,
                parameterSource
        );
        log.info("Executing query ({}) with parameters: {}", query, sqlParameterSourceExtractor(parameterSource));
    }

    private String createSelectQueryByFilter(ProductTypeListFilter filter, boolean countSelect) {
        StringBuilder query = new StringBuilder(countSelect
            ? SQL_COUNT_PRODUCT_TYPES
            : SQL_SELECT_PRODUCT_TYPES);

        if(StringUtils.isNotBlank(filter.getType())){
            query.append(format(" AND type ILIKE (:%s)", type));
        }

        if (!countSelect) {
            query.append(filter.getOrderBySql());
            if (filter.isPageable()) {
                query.append(format(" OFFSET (:%s) LIMIT (:%s)", FILTER_FIELDS.offset, FILTER_FIELDS.limit));
            }
        }

        log.info("Executing query ({})", query);

        return query.toString();
    }

    private SqlParameterSource createSqlParameterSourceByFilter(ProductTypeListFilter filter) {
        return new MapSqlParameterSource()
                .addValue(type.name(), filter.getType())
                .addValue(type.name(), filter.getType() + "%")
                .addValue(offset.name(), filter.getPageNumber() * filter.getPageSize())
                .addValue(limit.name(), filter.getPageSize());
    }

    private SqlParameterSource createSqlParameterSource(ProductType productType) {
        return new MapSqlParameterSource()
                .addValue(type.name(), productType.getType());
    }

    private final RowMapper<ProductType> rowMapper = (rs, rowNum) -> {
        ProductType result = new ProductType();
        result.setType(rs.getString("type"));
        result.setLastChangeDate(getTimestampOrNull(rs, "last_change_date"));
        result.setVersion(rs.getInt("version"));
        return result;
    };


    @Override
    protected String getEntityName() {
        return "product_types";
    }

    @Override
    protected String getCode(ProductType object) {
        if (object == null) {
            return null;
        }
        return String.valueOf(object.getType());
    }
}
