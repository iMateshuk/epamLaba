package com.epam.esm.dao.jdbc;

import com.epam.esm.dao.entity.TagEntity;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagExtractor implements ResultSetExtractor<TagEntity> {

    @Override
    public TagEntity extractData(ResultSet rs) throws SQLException, DataAccessException {

        return CreateEntity.createTagEntity(rs);
    }
}
