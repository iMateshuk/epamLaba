package com.epam.esm.dao.jdbc;

import com.epam.esm.dao.entity.GiftCertEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GiftCertMapper implements RowMapper<GiftCertEntity> {

    @Override
    public GiftCertEntity mapRow(ResultSet rs, int rowNum) throws SQLException {

        GiftCertEntity gc = new GiftCertEntity();

        gc.setId(rs.getInt("id"));
        gc.setName(rs.getString("name"));
        gc.setDescription(rs.getString("description"));
        gc.setPrice(rs.getFloat("price"));
        gc.setDuration(rs.getInt("duration"));
        gc.setCreateDate(rs.getTimestamp("create_date"));
        gc.setLastUpdateDate(rs.getTimestamp("last_update_date"));

        return gc;
    }
}
