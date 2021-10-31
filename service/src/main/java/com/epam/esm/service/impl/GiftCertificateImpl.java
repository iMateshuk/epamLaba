package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.dto.GiftCertificateConverter;
import com.epam.esm.service.dto.GiftCertificateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiftCertificateImpl implements GiftCertificateService {

    @Autowired
    GiftCertificateDAO gcDAO;

    @Override
    public void createGiftCertificate(GiftCertificateDTO gcDTO) {

        gcDAO.createGiftCertificate(GiftCertificateConverter.toEntity(gcDTO));
    }

    @Override
    public List<GiftCertificateDTO> searchGiftCertificates() {

        return GiftCertificateConverter.toDto(gcDAO.searchGiftCertificates());
    }

    @Override
    public GiftCertificateDTO searchGiftCertificate(int id) {

        return GiftCertificateConverter.toDto(gcDAO.searchGiftCertificate(id));
    }

    @Override
    public void updateGiftCertificate(GiftCertificateDTO gcDTO) {

        gcDAO.updateGiftCertificate(GiftCertificateConverter.toEntity(gcDTO));
    }

    @Override
    public void delGiftCertificate(int id) {

        gcDAO.delGiftCertificate(id);
    }
}
