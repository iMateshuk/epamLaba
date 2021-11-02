package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.entity.GiftCertificateEntity;
import com.epam.esm.dao.jdbc.GiftCertificateMapper;
import com.epam.esm.dao.util.GiftCertificateSQL;
import com.epam.esm.dao.util.GiftCertificateTagSQL;
import com.epam.esm.dao.util.QueryGenerator;
import com.epam.esm.dao.util.RequestedParameter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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

        return getGiftCertificate(id != null ? id : 0);
    }

    @Override
    public List<GiftCertificateEntity> getGiftCertificates() {

        return jdbcTemplate.query(GiftCertificateSQL.SELECT_ALL.getSQL(), new GiftCertificateMapper());
    }

    @Override
    public GiftCertificateEntity getGiftCertificate(int id) {

        return jdbcTemplate.queryForObject(GiftCertificateSQL.SELECT_ALL_W_ID.getSQL(), new GiftCertificateMapper(), id);
    }

    @Override
    public List<GiftCertificateEntity> getGiftCertificates(String tagName) {

        final String sql = GiftCertificateSQL.SELECT_W_TAG_NAME.getSQL();

        return jdbcTemplate.query(sql, new GiftCertificateMapper(), tagName);
    }

    @Override
    public List<GiftCertificateEntity> getGiftCertificates(Map<RequestedParameter, String> requestedParameters) {

        final String sql = QueryGenerator.sqlSearchWithParameters(requestedParameters);

        QueryGenerator.cleanMap(requestedParameters);

        requestedParameters.values().removeAll(Collections.singleton(null));

        //System.out.println(sql);

        return jdbcTemplate.query(sql, new GiftCertificateMapper(), requestedParameters.values().toArray());
    }

    @Override
    public GiftCertificateEntity updateGiftCertificate(GiftCertificateEntity giftCertificateEntity) {

        int id = giftCertificateEntity.getId();

        List<Object> params = prepareObjects(giftCertificateEntity);

        params.add(id);

        jdbcTemplate.update(GiftCertificateSQL.UPDATE_DATA_IF_NOT_NULL_EMPTY.getSQL(), params.toArray());

        return getGiftCertificate(id);
    }

    @Override
    public void delGiftCertificate(int id) {

        jdbcTemplate.update(GiftCertificateSQL.DEL_DB_CASCADE_W_ID.getSQL(), id);
    }

    @Override
    public void delGiftCertificateAndTagBundle(int id) {

        jdbcTemplate.update(GiftCertificateTagSQL.DEL_W_GC_ID.getSQL(), id);
    }

    @Override
    public void addGiftCertificateTag(int GiftCertificateId, String tagName) {

        jdbcTemplate.update(GiftCertificateTagSQL.INSERT_W_ID_NAME.getSQL(), GiftCertificateId, tagName);
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
