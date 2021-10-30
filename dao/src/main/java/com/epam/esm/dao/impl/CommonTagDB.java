package com.epam.esm.dao.impl;

import com.epam.esm.dao.CommonTagDAO;
import com.epam.esm.dao.entity.TagEntity;
import com.epam.esm.dao.jdbc.TagMapper;
import com.epam.esm.dao.util.CommonSQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommonTagDB implements CommonTagDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public TagEntity getTagEntityByName(String tagName) {

        final String sql = CommonSQL.SQL_TAG_SELECT_ID_A_NAME_W_NAME.getSQL();

        return jdbcTemplate.queryForObject(sql, TagEntity.class, tagName);
    }

    @Override
    public List<TagEntity> getListTag(int id) {

        final String sql = CommonSQL.SQL_TAG_SELECT_W_GC_ID.getSQL();

        return jdbcTemplate.query(sql, new TagMapper(), id);
    }
}
