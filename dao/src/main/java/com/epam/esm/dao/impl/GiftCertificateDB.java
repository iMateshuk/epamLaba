package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.entity.GiftCertificateEntity;
import com.epam.esm.dao.jdbc.GiftCertificateMapper;
import com.epam.esm.dao.util.GiftCertificateSQL;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GiftCertificateDB implements GiftCertificateDAO {

    private final JdbcTemplate jdbcTemplate;

    public GiftCertificateDB(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    @Override
    public GiftCertificateEntity createGiftCertificate(GiftCertificateEntity giftCertificateEntity) {

        Integer id;

        List<Object> params = prepareObjects(giftCertificateEntity);

        jdbcTemplate.update(GiftCertificateSQL.INSERT_GIFT_CERT.getSQL(), params.toArray());

        id = jdbcTemplate.queryForObject(GiftCertificateSQL.SELECT_LAST_INSERT_ID.getSQL(), Integer.class);

        return searchGiftCertificate(id != null ? id : 0);
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
    public List<GiftCertificateEntity> getListGiftCertificates(String tagName) {

        final String sql = GiftCertificateSQL.SELECT_W_TAG_NAME.getSQL();

        return jdbcTemplate.query(sql, new GiftCertificateMapper(), tagName);
    }

    @Override
    public GiftCertificateEntity updateGiftCertificate(GiftCertificateEntity giftCertificateEntity) {

        int id = giftCertificateEntity.getId();

        List<Object> params = prepareObjects(giftCertificateEntity);

        params.add(Timestamp.from(Instant.now()));
        params.add(id);

        jdbcTemplate.update(GiftCertificateSQL.UPDATE_DATA_IF_NOT_NULL_EMPTY.getSQL(), params.toArray());

        return searchGiftCertificate(id);
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
