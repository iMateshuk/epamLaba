package com.epam.esm.service.impl;

import com.epam.esm.dao.CommonDAO;
import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.entity.GiftCertificateEntity;
import com.epam.esm.dao.entity.TagEntity;
import com.epam.esm.service.CommonService;
import com.epam.esm.service.dto.GiftCertificateConverter;
import com.epam.esm.service.dto.GiftCertificateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    CommonDAO commonDAO;

    @Autowired
    TagDAO tagDAO;

    @Autowired
    GiftCertificateDAO gcDAO;

    public List<GiftCertificateDTO> getGiftCertificate(String tagName) {

        List<GiftCertificateEntity> listGiftCertificates = commonDAO.getListGiftCertificate(tagName);

        List<GiftCertificateDTO> gcDTOs = GiftCertificateConverter.toDto(listGiftCertificates);

        for (GiftCertificateDTO gcDTO : gcDTOs) {

            List<TagEntity> tags = commonDAO.getListTag(gcDTO.getId());

            for (TagEntity tag : tags) {

                gcDTO.getTagNames().add(tag.getName());

            }
        }

        return gcDTOs;
    }

    @Override
    @Transactional
    public void updateGiftCertWithTags(GiftCertificateDTO gcDTO) {

        for (String tagName : gcDTO.getTagNames()) {

            if (!tagDAO.isTagExist(tagName)) {

                tagDAO.createTag(tagName);
            }
        }

        //gcDAO.updateGiftCert(ConvertGiftCertDTOToEntity.getGiftCertEntity(gcDTO));

    }
}
