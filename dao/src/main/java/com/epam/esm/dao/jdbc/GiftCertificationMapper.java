package com.epam.esm.dao.jdbc;

import com.epam.esm.bean.GiftCert;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GiftCertificationMapper implements RowMapper {

    @Override
    public GiftCert mapRow(ResultSet rs, int rowNum) throws SQLException {

        GiftCert gc = new GiftCert();
        gc.setId(rs.getInt("id"));
        gc.setName(rs.getString("name"));
        gc.setDescription(rs.getString("description"));
        gc.setPrice(rs.getFloat("price"));
        gc.setDuration(rs.getInt("duration"));
        gc.setCreateDate(rs.getDate("create_date"));
        gc.setLastUpdateDate(rs.getDate("last_update_date"));

        return gc;
    }
}
