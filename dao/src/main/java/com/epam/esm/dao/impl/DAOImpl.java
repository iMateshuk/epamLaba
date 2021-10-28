package com.epam.esm.dao.impl;

import com.epam.esm.dao.entity.GCAndTagName;
import com.epam.esm.dao.entity.GiftCert;
import com.epam.esm.dao.DAO;
import com.epam.esm.dao.jdbc.GiftCertificationMapper;
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

        List<GiftCert> giftCerts = jdbcTemplate.query(sql, new GiftCertificationMapper());

        for (GiftCert gc: giftCerts) {

            answer += gc.getName() + " : " + gc.getDescription() + "; ";

        }

        return answer;
    }

    @Override
    public List<GiftCert> listGift() {

        final String sql = QuerySQL.SQL_GC_SELECT_ALL.getSQL();

        List<GiftCert> giftCerts = jdbcTemplate.query(sql, new GiftCertificationMapper());

        return giftCerts;
    }

    @Override
    public List<GCAndTagName> getGiftCert(int id) {

        final String sql = QuerySQL.SQL_GC_SELECT_W_ID.getSQL();

       List<GCAndTagName> gcAndTagNames = jdbcTemplate.query(sql, new GiftCertificationMapper(), id);

        return gcAndTagNames;
    }
}
