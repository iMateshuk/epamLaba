package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.entity.TagEntity;
import com.epam.esm.dao.jdbc.TagExtractor;
import com.epam.esm.dao.jdbc.TagMapper;
import com.epam.esm.dao.util.TagSQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagDB implements TagDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public TagEntity getTagEntityByName(String tagName) {

        final String sql = TagSQL.SQL_TAG_SELECT_ID_A_NAME_W_NAME.getSQL();

        return jdbcTemplate.query(sql, new TagExtractor(), tagName);
    }

    @Override
    public List<TagEntity> getListTag(int id) {

        final String sql = TagSQL.SQL_TAG_SELECT_W_GC_ID.getSQL();

        return jdbcTemplate.query(sql, new TagMapper(), id);
    }
}
