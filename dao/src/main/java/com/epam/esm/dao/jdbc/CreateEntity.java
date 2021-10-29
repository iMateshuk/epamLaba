package com.epam.esm.dao.jdbc;

import com.epam.esm.dao.entity.GiftCertificateEntity;
import com.epam.esm.dao.entity.TagEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

class CreateEntity {

    static TagEntity createTagEntity(ResultSet rs) throws SQLException {

        TagEntity tag = new TagEntity();

        tag.setId(rs.getInt("id"));
        tag.setName(rs.getString("name"));

        return tag;
    }

    static GiftCertificateEntity createGiftCertificateEntity(ResultSet rs) throws SQLException {

        GiftCertificateEntity gc = new GiftCertificateEntity();

        gc.setId(rs.getInt("id"));
        gc.setName(rs.getString("name"));
        gc.setDescription(rs.getString("description"));
        gc.setPrice(rs.getFloat("price"));
        gc.setDuration(rs.getInt("duration"));
        gc.setCreateDate(rs.getDate("create_date"));
        gc.setLastUpdateDate(rs.getDate("last_update_date"));

        return gc;
    }

}
