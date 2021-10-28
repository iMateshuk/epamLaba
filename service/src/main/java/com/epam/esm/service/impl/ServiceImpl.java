package com.epam.esm.service.impl;

import com.epam.esm.dao.DAO;
import com.epam.esm.service.GCService;
import com.epam.esm.service.dto.ConvertDTO;
import com.epam.esm.service.dto.GiftCertDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceImpl implements GCService {

    @Autowired
    DAO dao;

    @Override
    public String list() {

        return dao.list();
    }

    @Override
    public List<GiftCertDTO> listGift() {

        return ConvertDTO.getGiftCertDTO(dao.listGift());
    }

    @Override
    public GiftCertDTO getGiftCert(int id) {

        /*return ConvertDTO.getGiftCertDTO(dao.getGiftCert(id));*/
        return null;
    }
}
