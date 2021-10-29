package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.entity.GiftCertificateEntity;
import com.epam.esm.dao.jdbc.GiftCertificateMapper;
import com.epam.esm.dao.util.GiftCertificateSQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GiftCertificateDB implements GiftCertificateDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<GiftCertificateEntity> getListGiftCertificate(String tagName) {

        final String sql = GiftCertificateSQL.SQL_GC_SELECT_W_TAG_NAME.getSQL();

        return jdbcTemplate.query(sql, new GiftCertificateMapper(), tagName);
    }

}
