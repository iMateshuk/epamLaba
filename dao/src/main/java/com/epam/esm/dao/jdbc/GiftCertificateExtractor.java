package com.epam.esm.dao.jdbc;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GiftCertificateExtractor implements ResultSetExtractor {


    @Override
    public Object extractData(ResultSet rs) throws SQLException, DataAccessException {

        return CreateEntity.createGiftCertificateEntity(rs);
    }
}
