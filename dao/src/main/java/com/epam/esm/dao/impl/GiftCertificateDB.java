package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.entity.GiftCertificateEntity;
import com.epam.esm.dao.jdbc.GiftCertificateMapper;
import com.epam.esm.dao.util.GiftCertificateSQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GiftCertificateDB implements GiftCertificateDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void createGiftCertificate(GiftCertificateEntity giftCertificateEntity) {

        Timestamp timestamp = Timestamp.from(Instant.now());

        List<Object> params = prepareObjects(giftCertificateEntity);

        params.add(timestamp);
        params.add(timestamp);

        jdbcTemplate.update(GiftCertificateSQL.INSERT_GIFT_CERT.getSQL(), params.stream().toArray(Object[]::new));
    }

    @Override
    public List<GiftCertificateEntity> searchGiftCertificates() {

        return jdbcTemplate.query(GiftCertificateSQL.SELECT_ALL.getSQL(), new GiftCertificateMapper());
    }

    @Override
    public GiftCertificateEntity searchGiftCertificate(int id) {

        return jdbcTemplate.queryForObject(GiftCertificateSQL.SELECT_ALL_W_ID.getSQL(), new GiftCertificateMapper(), id);
    }

    @Override
    public void updateGiftCertificate(GiftCertificateEntity giftCertificateEntity) {

        List<Object> params = prepareObjects(giftCertificateEntity);

        params.add(Timestamp.from(Instant.now()));
        params.add(giftCertificateEntity.getId());

        jdbcTemplate.update(GiftCertificateSQL.UPDATE_DATA_IF_NOT_NULL_EMPTY.getSQL(), params.stream().toArray(Object[]::new));
    }

    @Override
    public void delGiftCertificate(int id) {

        jdbcTemplate.update(GiftCertificateSQL.DEL_DB_CASCADE_W_ID.getSQL(), id);
    }

    private List<Object> prepareObjects(GiftCertificateEntity giftCertificateEntity) {

        List<Object> params = new ArrayList<>();

        params.add(giftCertificateEntity.getName());
        params.add(giftCertificateEntity.getDescription());
        params.add(giftCertificateEntity.getPrice());
        params.add(giftCertificateEntity.getDuration());

        return params;
    }
}
