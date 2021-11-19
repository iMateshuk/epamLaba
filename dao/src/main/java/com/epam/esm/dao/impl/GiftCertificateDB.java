package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.entity.GiftCertificateEntity;
import com.epam.esm.dao.config.GiftCertificateMapper;
import com.epam.esm.dao.util.GiftCertificateSQL;
import com.epam.esm.dao.util.GiftCertificateTagSQL;
import com.epam.esm.dao.util.QueryCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class GiftCertificateDB implements GiftCertificateDAO {

    private final JdbcTemplate jdbcTemplate;
    private final Map<GiftCertificateSQL, String> giftCertificateSQLs;
    private final GiftCertificateMapper giftCertificateMapper;

    public GiftCertificateDB(JdbcTemplate jdbcTemplate,
                             Map<GiftCertificateSQL, String> createGiftCertificateSQLs,
                             GiftCertificateMapper giftCertificateMapper) {

        this.jdbcTemplate = jdbcTemplate;
        this.giftCertificateSQLs = createGiftCertificateSQLs;
        this.giftCertificateMapper = giftCertificateMapper;
    }

    @Transactional
    @Override
    public GiftCertificateEntity createGiftCertificate(GiftCertificateEntity giftCertificateEntity) {

        jdbcTemplate.update(giftCertificateSQLs.get(GiftCertificateSQL.INSERT_GIFT_CERT), prepareObjects(giftCertificateEntity).toArray());

        return jdbcTemplate.queryForObject(GiftCertificateSQL.SELECT_W_NAME.getSQL(), giftCertificateMapper, giftCertificateEntity.getName());
    }

    @Override
    public List<GiftCertificateEntity> getGiftCertificates() {

        return jdbcTemplate.query(GiftCertificateSQL.SELECT_ALL.getSQL(), giftCertificateMapper);
    }

    @Override
    public GiftCertificateEntity getGiftCertificate(int id) {

        return jdbcTemplate.queryForObject(GiftCertificateSQL.SELECT_W_ID.getSQL(), giftCertificateMapper, id);
    }

    @Override
    public List<GiftCertificateEntity> getGiftCertificates(String tagName) {

        return jdbcTemplate.query(GiftCertificateSQL.SELECT_W_TAG_NAME.getSQL(), giftCertificateMapper, tagName);
    }

    @Override
    public List<GiftCertificateEntity> getGiftCertificates(Map<String, String> requestedParameters) {

        final String sql = QueryCreator.buildSql(requestedParameters);

        QueryCreator.removeKeyMatchSort(requestedParameters);

        return jdbcTemplate.query(sql, giftCertificateMapper, requestedParameters.values().toArray());
    }

    @Override
    public GiftCertificateEntity updateGiftCertificate(GiftCertificateEntity giftCertificateEntity) {

        int id = giftCertificateEntity.getId();

        List<Object> params = prepareObjects(giftCertificateEntity);

        params.add(id);

        jdbcTemplate.update(giftCertificateSQLs.get(GiftCertificateSQL.UPDATE_DATA_IF_NOT_NULL_EMPTY), params.toArray());

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
