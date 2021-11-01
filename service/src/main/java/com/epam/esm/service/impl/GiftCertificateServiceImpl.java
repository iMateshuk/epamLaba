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
import java.util.Map;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDAO giftCertificateDAO;
    private final TagDAO tagDAO;

    public GiftCertificateServiceImpl(GiftCertificateDAO giftCertificateDAO, TagDAO tagDAO) {

        this.giftCertificateDAO = giftCertificateDAO;
        this.tagDAO = tagDAO;
    }

    @Override
    public GiftCertificateDTO createGiftCertificate(GiftCertificateDTO gcDTO) {


        return GiftCertificateConverter.toDto(giftCertificateDAO.createGiftCertificate(GiftCertificateConverter.toEntity(gcDTO)));
    }

    @Override
    public List<GiftCertificateDTO> searchGiftCertificates() {

        List<GiftCertificateDTO> giftCertificateDTOs = GiftCertificateConverter.toDto(giftCertificateDAO.searchGiftCertificates());

        addTagToDTO(giftCertificateDTOs);

        return giftCertificateDTOs;
    }

    @Override
    public GiftCertificateDTO searchGiftCertificate(int id) {

        GiftCertificateDTO giftCertificateDTO = GiftCertificateConverter.toDto(giftCertificateDAO.searchGiftCertificate(id));

        addTagToDTO(giftCertificateDTO);

        return giftCertificateDTO;
    }

    @Override
    public List<GiftCertificateDTO> searchGiftCertificates(Map<String, String> allRequestParams) {

        /*
        Get certificates with tags (all params are optional and can be used in conjunction):
            by tag name (ONE tag) - tagName
            search by part of name/description (can be implemented, using DB function call) - searchName, searchDescription
            sort by date or by name ASC/DESC - sortDate, sortName
        */

        return null;
    }

    @Override
    public List<GiftCertificateDTO> getGiftCertificates(String tagName) {

        List<GiftCertificateEntity> listGiftCertificates = giftCertificateDAO.getListGiftCertificates(tagName);

        List<GiftCertificateDTO> giftCertificateDTOs = GiftCertificateConverter.toDto(listGiftCertificates);

        addTagToDTO(giftCertificateDTOs);

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

    private void addTagToDTO(List<GiftCertificateDTO> giftCertificateDTOs) {

        for (GiftCertificateDTO giftCertificateDTO : giftCertificateDTOs) {

            addTagToDTO(giftCertificateDTO);
        }
    }

    private void addTagToDTO(GiftCertificateDTO giftCertificateDTO) {

        List<TagEntity> tags = tagDAO.getListTag(giftCertificateDTO.getId());

        for (TagEntity tagEntity : tags) {

            giftCertificateDTO.getTags().add(TagConverter.toDto(tagEntity));

        }
    }
}
