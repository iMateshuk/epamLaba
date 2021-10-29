package com.epam.esm.dao.jdbc;

import com.epam.esm.dao.entity.GiftCertificateEntity;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GiftCertificateExtractor implements ResultSetExtractor<GiftCertificateEntity> {


    @Override
    public GiftCertificateEntity extractData(ResultSet rs) throws SQLException, DataAccessException {

        return CreateEntity.createGiftCertificateEntity(rs);
    }
}
