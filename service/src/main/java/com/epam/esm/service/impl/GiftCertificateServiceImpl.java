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
import com.epam.esm.service.util.CheckData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public GiftCertificateDTO createGiftCertificate(GiftCertificateDTO requestGiftCertificateDTO) {

        CheckData.giftCertificate(requestGiftCertificateDTO);

        CheckData.listEmpty(requestGiftCertificateDTO.getTags());

        GiftCertificateDTO createdGiftCertificateDTO = GiftCertificateConverter
                .toDto(giftCertificateDAO.createGiftCertificate(GiftCertificateConverter.toEntity(requestGiftCertificateDTO)));

        checkTagNameAndBundleWithGiftCertificate(requestGiftCertificateDTO, createdGiftCertificateDTO.getId());

        addTagToDTO(createdGiftCertificateDTO);

        return createdGiftCertificateDTO;
    }

    @Override
    public List<GiftCertificateDTO> searchGiftCertificates() {

        List<GiftCertificateDTO> createdGiftCertificateDTOs = GiftCertificateConverter
                .toDto(giftCertificateDAO.searchGiftCertificates());

        addTagToDTO(createdGiftCertificateDTOs);

        return createdGiftCertificateDTOs;
    }

    @Override
    public GiftCertificateDTO searchGiftCertificate(int id) {

        CheckData.isPositiveInteger(id);

        GiftCertificateDTO createdGiftCertificateDTO = GiftCertificateConverter
                .toDto(giftCertificateDAO.searchGiftCertificate(id));

        addTagToDTO(createdGiftCertificateDTO);

        return createdGiftCertificateDTO;
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

        CheckData.stringValidator(tagName);

        List<GiftCertificateEntity> listGiftCertificates = giftCertificateDAO.getListGiftCertificates(tagName);

        List<GiftCertificateDTO> createdGiftCertificateDTOs = GiftCertificateConverter.toDto(listGiftCertificates);

        addTagToDTO(createdGiftCertificateDTOs);

        return createdGiftCertificateDTOs;
    }

    @Transactional
    @Override
    public GiftCertificateDTO updateGiftCertWithTags(GiftCertificateDTO requestGiftCertificateDTO) {

        GiftCertificateDTO updatedGiftCertificateDTO = updateGiftCertificate(requestGiftCertificateDTO);

        CheckData.giftCertificatePartialField(requestGiftCertificateDTO);

        if (!requestGiftCertificateDTO.getTags().isEmpty()) {

            int id = requestGiftCertificateDTO.getId();

            giftCertificateDAO.delGiftCertificateAndTagBundle(id);

            checkTagNameAndBundleWithGiftCertificate(requestGiftCertificateDTO, id);
        }

        addTagToDTO(updatedGiftCertificateDTO);

        return updatedGiftCertificateDTO;
    }

    @Override
    public GiftCertificateDTO updateGiftCertificate(GiftCertificateDTO requestGiftCertificateDTO) {

        return GiftCertificateConverter.toDto(giftCertificateDAO
                .updateGiftCertificate(GiftCertificateConverter.toEntity(requestGiftCertificateDTO)));
    }

    @Override
    public void delGiftCertificate(int id) {

        CheckData.isPositiveInteger(id);

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

    private void checkTagNameAndBundleWithGiftCertificate(GiftCertificateDTO requestGiftCertificateDTO, int id) {

        String tagName;

        for (TagDTO tagDTO : requestGiftCertificateDTO.getTags()) {

            tagName = tagDTO.getName();

            if (!tagDAO.isTagExist(tagName)) {

                tagDAO.createTag(tagName);
            }

            giftCertificateDAO.addGiftCertificateTag(id, tagName);
        }
    }
}
