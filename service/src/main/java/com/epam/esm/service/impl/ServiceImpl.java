package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.CommonTagDAO;
import com.epam.esm.dao.entity.GiftCertificateEntity;
import com.epam.esm.dao.entity.TagEntity;
import com.epam.esm.service.ServiceCommon;
import com.epam.esm.service.dto.ConvertDTO;
import com.epam.esm.service.dto.GiftCertificateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceImpl implements ServiceCommon {

    @Autowired
    GiftCertificateDAO giftCertificateDAO;

    @Autowired
    CommonTagDAO tagDAO;

    public List<GiftCertificateDTO> getGiftCertificate(String tagName) {

        List<GiftCertificateEntity> listGiftCertificates = giftCertificateDAO.getListGiftCertificate(tagName);

        List<GiftCertificateDTO> gcDTOs = ConvertDTO.getGiftCertDTO(listGiftCertificates);

        for (GiftCertificateDTO gcDTO : gcDTOs) {

            List<TagEntity> tags = tagDAO.getListTag(gcDTO.getId());

            for (TagEntity tag : tags) {

                gcDTO.getTagNames().add(tag.getName());

            }
        }

        return gcDTOs;
    }
}
