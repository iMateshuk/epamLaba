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
                .toDto(giftCertificateDAO.getGiftCertificates());

        addTagToDTO(createdGiftCertificateDTOs);

        return createdGiftCertificateDTOs;
    }

    @Override
    public GiftCertificateDTO searchGiftCertificate(int id) {

        CheckData.isPositiveInteger(id);

        GiftCertificateDTO createdGiftCertificateDTO = GiftCertificateConverter
                .toDto(giftCertificateDAO.getGiftCertificate(id));

        addTagToDTO(createdGiftCertificateDTO);

        return createdGiftCertificateDTO;
    }

    @Override
    public List<GiftCertificateDTO> searchGiftCertificates(Map<String, String> allRequestParams) {

        Map<String, String> parameters = CheckData.createMapParameter(allRequestParams);

        CheckData.mapEmpty(parameters);

        List<GiftCertificateDTO> createdGiftCertificateDTOs = GiftCertificateConverter
                .toDto(giftCertificateDAO.getGiftCertificates(parameters));

        addTagToDTO(createdGiftCertificateDTOs);

        return createdGiftCertificateDTOs;
    }

    @Override
    public List<GiftCertificateDTO> getGiftCertificates(String tagName) {

        CheckData.tagNameLengthValidator(tagName);

        List<GiftCertificateEntity> listGiftCertificates = giftCertificateDAO.getGiftCertificates(tagName);

        List<GiftCertificateDTO> createdGiftCertificateDTOs = GiftCertificateConverter.toDto(listGiftCertificates);

        addTagToDTO(createdGiftCertificateDTOs);

        return createdGiftCertificateDTOs;
    }

    @Transactional
    @Override
    public GiftCertificateDTO updateGiftCertificateWithTags(GiftCertificateDTO requestGiftCertificateDTO) {

        CheckData.giftCertificatePartialField(requestGiftCertificateDTO);

        GiftCertificateDTO updatedGiftCertificateDTO = updateGiftCertificate(requestGiftCertificateDTO);

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
        CheckData.isZeroInteger(id);

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
