package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.entity.GiftCertificateEntity;
import com.epam.esm.dao.entity.TagEntity;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.dto.GiftCertificateConverter;
import com.epam.esm.service.dto.GiftCertificateDTO;
import com.epam.esm.service.dto.TagConverter;
import com.epam.esm.service.dto.TagDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiftCertificateImpl implements GiftCertificateService {

    private final GiftCertificateDAO giftCertificateDAO;
    private final TagDAO tagDAO;

    public GiftCertificateImpl(GiftCertificateDAO giftCertificateDAO, TagDAO tagDAO) {

        this.giftCertificateDAO = giftCertificateDAO;
        this.tagDAO = tagDAO;
    }

    @Override
    public GiftCertificateDTO createGiftCertificate(GiftCertificateDTO gcDTO) {

        return GiftCertificateConverter.toDto(giftCertificateDAO.createGiftCertificate(GiftCertificateConverter.toEntity(gcDTO)));
    }

    @Override
    public List<GiftCertificateDTO> searchGiftCertificates() {

        return GiftCertificateConverter.toDto(giftCertificateDAO.searchGiftCertificates());
    }

    @Override
    public GiftCertificateDTO searchGiftCertificate(int id) {

        return GiftCertificateConverter.toDto(giftCertificateDAO.searchGiftCertificate(id));
    }

    @Override
    public List<GiftCertificateDTO> getGiftCertificates(String tagName) {

        List<GiftCertificateEntity> listGiftCertificates = giftCertificateDAO.getListGiftCertificates(tagName);

        List<GiftCertificateDTO> giftCertificateDTOs = GiftCertificateConverter.toDto(listGiftCertificates);

        for (GiftCertificateDTO giftCertificateDTO : giftCertificateDTOs) {

            List<TagEntity> tags = tagDAO.getListTag(giftCertificateDTO.getId());

            for (TagEntity tagEntity : tags) {

                giftCertificateDTO.getTags().add(TagConverter.toDto(tagEntity));

            }
        }

        return giftCertificateDTOs;
    }

    @Override
    public GiftCertificateDTO updateGiftCertWithTags(GiftCertificateDTO giftCertificateDTO) {

        String tagName;

        for (TagDTO tagDTO : giftCertificateDTO.getTags()) {

            tagName = tagDTO.getName();

            if (!tagDAO.isTagExist(tagName)) {

                tagDAO.createTag(tagName);
            }
        }

        //gcDAO.updateGiftCert(ConvertGiftCertDTOToEntity.getGiftCertEntity(gcDTO));

        return null;
    }

    @Override
    public GiftCertificateDTO updateGiftCertificate(GiftCertificateDTO gcDTO) {

        return GiftCertificateConverter.toDto(giftCertificateDAO.updateGiftCertificate(GiftCertificateConverter.toEntity(gcDTO)));
    }

    @Override
    public void delGiftCertificate(int id) {

        giftCertificateDAO.delGiftCertificate(id);
    }
}
