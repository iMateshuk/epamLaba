package com.epam.esm.dao.jdbc;

import com.epam.esm.dao.entity.GiftCertificateEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GiftCertificateMapper implements RowMapper<GiftCertificateEntity> {

    @Override
    public GiftCertificateEntity mapRow(ResultSet rs, int rowNum) throws SQLException {

        return CreateEntity.createGiftCertificateEntity(rs);
    }
}
