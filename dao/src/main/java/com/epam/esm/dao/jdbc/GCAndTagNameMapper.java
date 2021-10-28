package com.epam.esm.dao.jdbc;

import com.epam.esm.dao.entity.GCAndTagName;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GCAndTagNameMapper implements RowMapper {

    @Override
    public GCAndTagName mapRow(ResultSet rs, int rowNum) throws SQLException {

        GCAndTagName gc = new GCAndTagName();

        gc.setId(rs.getInt("id"));
        gc.setName(rs.getString("name"));
        gc.setDescription(rs.getString("description"));
        gc.setPrice(rs.getFloat("price"));
        gc.setDuration(rs.getInt("duration"));
        gc.setCreateDate(rs.getDate("create_date"));
        gc.setLastUpdateDate(rs.getDate("last_update_date"));

        gc.setTagName(rs.getString("tag_name"));

        return gc;
    }
}
