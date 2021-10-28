package com.epam.esm.dao;

import com.epam.esm.dao.entity.GCAndTagName;
import com.epam.esm.dao.entity.GiftCert;

import java.util.List;

public interface DAO {

    public String list();

    public List<GiftCert> listGift();

    public List<GCAndTagName> getGiftCert(int id);
}
