package com.epam.esm.dao.impl;

import com.epam.esm.bean.GiftCert;
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

        String answer = "in DAO";

        List<GiftCert> giftCerts = jdbcTemplate.query(sql, new GiftCertificationMapper());

        for (GiftCert gc: giftCerts) {

            answer += gc.getName() + " : " + gc.getDescription();

        }

        return answer;
    }
}
