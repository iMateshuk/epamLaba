package com.epam.esm.service.impl;

import com.epam.esm.dao.CommonDAO;
import com.epam.esm.dao.entity.GiftCertEntity;
import com.epam.esm.dao.entity.TagEntity;
import com.epam.esm.service.ServiceCommon;
import com.epam.esm.service.dto.ConvertEntityToGiftCertDTO;
import com.epam.esm.service.dto.GiftCertDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommonServiceImpl implements ServiceCommon {

    @Autowired
    CommonDAO commonDAO;

    @Autowired
    CommonDAO tagDAO;

    public List<GiftCertDTO> getGiftCertificate(String tagName) {

        List<GiftCertEntity> listGiftCertificates = commonDAO.getListGiftCertificate(tagName);

        List<GiftCertDTO> gcDTOs = ConvertEntityToGiftCertDTO.getGiftCertDTO(listGiftCertificates);

        for (GiftCertDTO gcDTO : gcDTOs) {

            List<TagEntity> tags = tagDAO.getListTag(gcDTO.getId());

            for (TagEntity tag : tags) {

                gcDTO.getTagNames().add(tag.getName());

            }
        }

        return gcDTOs;
    }
}
