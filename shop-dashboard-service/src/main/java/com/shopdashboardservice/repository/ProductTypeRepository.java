package com.shopdashboardservice.repository;

import com.shopdashboardservice.model.ProductType;
import com.shopdashboardservice.model.listfilters.ProductTypesListFilter;
import com.shopdashboardservice.model.listfilters.ProductTypesListFilter.FILTER_FIELDS;
import java.sql.PreparedStatement;
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

import static com.shopdashboardservice.model.listfilters.ProductTypesListFilter.FILTER_FIELDS.id;
import static com.shopdashboardservice.model.listfilters.ProductTypesListFilter.FILTER_FIELDS.type;
import static com.shopdashboardservice.model.listfilters.ProductTypesListFilter.FILTER_FIELDS.offset;
import static com.shopdashboardservice.model.listfilters.ProductTypesListFilter.FILTER_FIELDS.limit;

import static com.shopdashboardservice.utils.JdbcUtils.getTimestampOrNull;
import static java.lang.String.format;


@Slf4j
@Repository
public class ProductTypeRepository extends BaseRepository<ProductType> {

    private static final String SQL_SELECT_PRODUCT_TYPES =
        "SELECT * FROM shop_dashboard.product_types WHERE 1=1";

    private static final String SQL_COUNT_PRODUCT_TYPES =
        "SELECT count(*) FROM shop_dashboard.product_types WHERE 1=1";

    private static final String SQL_INSERT_PRODUCT_TYPE =
        "INSERT INTO shop_dashboard.product_types (type) VALUES (?) RETURNING version, last_change_date;";

    private static final String SQL_UPDATE_PRODUCT_TYPE =
        "UPDATE shop_dashboard.product_types SET type=? WHERE id=? RETURNING version, last_change_date;";

    private static final String SQL_DELETE_PRODUCT_TYPE =
        "DELETE FROM shop_dashboard.product_types WHERE id=?";

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public ProductTypeRepository( JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    public List<ProductType> getProductTypesByFilter(ProductTypesListFilter filter){
        return namedParameterJdbcTemplate.query(
                createSelectQueryByFilter(filter, false),
                createSqlParameterSourceByFilter(filter),
                rowMapper
        );
    }

    public Integer getTotalRowCount(ProductTypesListFilter filter) {
        return namedParameterJdbcTemplate.queryForObject(
                createSelectQueryByFilter(filter, true),
                createSqlParameterSourceByFilter(filter),
                Integer.class
        );
    }

    public ProductType getProductTypeById(Long id) {
        List<ProductType> productTypes = getProductTypesByFilter(new ProductTypesListFilter().setId(id));

        return productTypes.isEmpty()
                ? null
                : productTypes.get(0);
    }

    public ProductType addProductType(ProductType productType) {
        Map<String, Object> insertResult = jdbcTemplate.query(con -> {
            PreparedStatement ps = con.prepareStatement(SQL_INSERT_PRODUCT_TYPE);
            ps.setString(1, productType.getType());

            return ps;
        }, this::extractUpdateResult);
        handleOptimisticLock(productType, insertResult);
        return productType;
    }

    public ProductType updateProductType(ProductType productType) {
        Map<String, Object> updateResult = jdbcTemplate.query(con -> {
            PreparedStatement ps = con.prepareStatement(SQL_UPDATE_PRODUCT_TYPE);
            ps.setString(1, productType.getType());
            ps.setLong(2, productType.getId());

            return ps;
        }, this::extractUpdateResult);
        handleOptimisticLock(productType, updateResult);
        return productType;
    }

    public void deleteProductType(Long id) {
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(SQL_DELETE_PRODUCT_TYPE);
            ps.setLong(1, id);
            return ps;
        });
    }

    private String createSelectQueryByFilter(ProductTypesListFilter filter, boolean countSelect) {
        StringBuilder query = new StringBuilder(countSelect
            ? SQL_COUNT_PRODUCT_TYPES
            : SQL_SELECT_PRODUCT_TYPES);

        if(filter.getId() != null){
            query.append(format(" AND id=(:%s)", id));
        }

        if(StringUtils.isNotBlank(filter.getType())){
            query.append(format(" AND type ILIKE (:%s)", type));
        }

        if (!countSelect) {
            query.append(filter.getOrderBySql());
            if (filter.isPageable()) {
                query.append(format(" OFFSET (:%s) LIMIT (:%s)", FILTER_FIELDS.offset, FILTER_FIELDS.limit));
            }
        }

        return query.toString();
    }

    private SqlParameterSource createSqlParameterSourceByFilter(ProductTypesListFilter filter) {
        return new MapSqlParameterSource()
                .addValue(id.name(), filter.getId())
                .addValue(type.name(), filter.getType() + "%")
                .addValue(offset.name(), filter.getPageNumber() * filter.getPageSize())
                .addValue(limit.name(), filter.getPageSize());
    }

    private final RowMapper<ProductType> rowMapper = (rs, rowNum) -> {
        ProductType result = new ProductType();
        result.setId(rs.getLong("id"));
        result.setType(rs.getString("type"));
        result.setLastChangeDate(getTimestampOrNull(rs, "last_change_date"));
        result.setVersion(rs.getInt("version"));
        return result;
    };


    @Override
    protected String getEntityName() {
        return "product_types";
    }
}
