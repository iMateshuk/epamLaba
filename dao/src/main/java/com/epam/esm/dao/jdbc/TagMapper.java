package com.epam.esm.dao.jdbc;

import com.epam.esm.dao.entity.TagEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagMapper implements RowMapper<TagEntity> {

    @Override
    public TagEntity mapRow(ResultSet rs, int rowNum) throws SQLException {

        TagEntity tag = new TagEntity();

        tag.setId(rs.getInt("id"));
        tag.setName(rs.getString("name"));

        return tag;
    }

}
