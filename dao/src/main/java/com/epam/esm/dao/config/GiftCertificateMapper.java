package com.epam.esm.dao.config;

import com.epam.esm.dao.entity.GiftCertificateEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GiftCertificateMapper implements RowMapper<GiftCertificateEntity> {

    @Override
    public GiftCertificateEntity mapRow(ResultSet rs, int rowNum) throws SQLException {

        GiftCertificateEntity gc = new GiftCertificateEntity();

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
