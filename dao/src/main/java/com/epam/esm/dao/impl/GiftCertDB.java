package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertDAO;
import com.epam.esm.dao.entity.GiftCertEntity;
import com.epam.esm.dao.jdbc.GiftCertMapper;
import com.epam.esm.dao.util.GiftCertSQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GiftCertDB implements GiftCertDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void createGiftCert(GiftCertEntity giftCertEntity) {

        Timestamp timestamp = Timestamp.from(Instant.now());

        List<Object> params = prepareObjects(giftCertEntity);

        params.add(timestamp);
        params.add(timestamp);

        jdbcTemplate.update(GiftCertSQL.INSERT_GIFT_CERT.getSQL(), params.stream().toArray(Object[]::new));
    }

    @Override
    public List<GiftCertEntity> searchGiftCerts() {

        return jdbcTemplate.query(GiftCertSQL.SELECT_ALL.getSQL(), new GiftCertMapper());
    }

    @Override
    public GiftCertEntity searchGiftCert(int id) {

        return jdbcTemplate.queryForObject(GiftCertSQL.SELECT_ALL_W_ID.getSQL(), new GiftCertMapper(), id);
    }

    @Override
    public void updateGiftCert(GiftCertEntity giftCertEntity) {

        List<Object> params = prepareObjects(giftCertEntity);

        params.add(Timestamp.from(Instant.now()));
        params.add(giftCertEntity.getId());

        jdbcTemplate.update(GiftCertSQL.UPDATE_DATA_IF_NOT_NULL_EMPTY.getSQL(), params.stream().toArray(Object[]::new));
    }

    @Override
    public void delGiftCert(int id) {

        jdbcTemplate.update(GiftCertSQL.DEL_DB_CASCADE_W_ID.getSQL(), id);
    }

    private List<Object> prepareObjects(GiftCertEntity giftCertEntity) {

        List<Object> params = new ArrayList<>();

        params.add(giftCertEntity.getName());
        params.add(giftCertEntity.getDescription());
        params.add(giftCertEntity.getPrice());
        params.add(giftCertEntity.getDuration());

        return params;
    }
}
