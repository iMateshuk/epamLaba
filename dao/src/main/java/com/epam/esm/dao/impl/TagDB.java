package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.entity.TagEntity;
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
    public void createTag(String name) {

        jdbcTemplate.update(TagSQL.INSERT_TAG.getSQL(), name);
    }

    @Override
    public List<TagEntity> searchTags() {

        return jdbcTemplate.query(TagSQL.SELECT_ALL.getSQL(), new TagMapper());
    }

    @Override
    public TagEntity searchTag(int id) {

        return jdbcTemplate.queryForObject(TagSQL.SELECT_ALL_W_ID.getSQL(), new TagMapper(), id);
    }

    @Override
    public void deleteTag(int id) {

        jdbcTemplate.update(TagSQL.DEL_DB_CASCADE_W_ID.getSQL(), id);
    }
}
