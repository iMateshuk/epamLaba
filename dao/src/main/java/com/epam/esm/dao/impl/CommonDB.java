package com.epam.esm.dao.impl;

import com.epam.esm.dao.CommonDAO;
import com.epam.esm.dao.entity.GiftCertificateEntity;
import com.epam.esm.dao.entity.TagEntity;
import com.epam.esm.dao.jdbc.GiftCertificateMapper;
import com.epam.esm.dao.jdbc.TagMapper;
import com.epam.esm.dao.util.CommonSQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommonDB implements CommonDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<GiftCertificateEntity> getListGiftCertificate(String tagName) {

        final String sql = CommonSQL.SELECT_W_TAG_NAME.getSQL();

        return jdbcTemplate.query(sql, new GiftCertificateMapper(), tagName);
    }

    @Override
    public List<TagEntity> getListTag(int id) {

        final String sql = CommonSQL.SELECT_W_GC_ID.getSQL();

        return jdbcTemplate.query(sql, new TagMapper(), id);
    }
}
