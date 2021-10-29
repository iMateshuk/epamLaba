package com.epam.esm.dao.impl;

import com.epam.esm.dao.DAO;
import com.epam.esm.dao.entity.GiftCertificateEntity;
import com.epam.esm.dao.entity.TagEntity;
import com.epam.esm.dao.jdbc.GiftCertificateMapper;
import com.epam.esm.dao.jdbc.TagExtractor;
import com.epam.esm.dao.jdbc.TagMapper;
import com.epam.esm.dao.util.QuerySQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DAOImpl implements DAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public String list() {

        final String sql = QuerySQL.SQL_GC_SELECT_ALL.getSQL();

        String answer = "in DAO: ";

        List<GiftCertificateEntity> giftCerts = jdbcTemplate.query(sql, new GiftCertificateMapper());

        for (GiftCertificateEntity gc: giftCerts) {

            answer += gc.getName() + " : " + gc.getDescription() + "; ";

        }

        return answer;
    }

    @Override
    public List<GiftCertificateEntity> getListGiftCertificate() {

        final String sql = QuerySQL.SQL_GC_SELECT_ALL.getSQL();

        return jdbcTemplate.query(sql, new GiftCertificateMapper());
    }

    @Override
    public List<GiftCertificateEntity> getListGiftCertificate(String tagName) {

        final String sql = QuerySQL.SQL_GC_SELECT_W_TAG_NAME.getSQL();

        return jdbcTemplate.query(sql, new GiftCertificateMapper(), tagName);
    }

    @Override
    public TagEntity getTagEntityByName(String tagName) {

        final String sql = QuerySQL.SQL_TAG_SELECT_ID_A_NAME_W_NAME.getSQL();

        return (TagEntity) jdbcTemplate.query(sql, new TagExtractor(), tagName);
    }

    @Override
    public List<TagEntity> getListTag() {

        final String sql = QuerySQL.SQL_TAG_SELECT_ALL.getSQL();

        return jdbcTemplate.query(sql, new TagMapper());
    }

    @Override
    public List<TagEntity> getListTag(int id) {

        final String sql = QuerySQL.SQL_TAG_SELECT_W_GC_ID.getSQL();

        return jdbcTemplate.query(sql, new TagMapper(), id);
    }
}
