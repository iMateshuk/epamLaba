package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.entity.GiftCertificateEntity;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.dto.GiftCertificateConverter;
import com.epam.esm.service.dto.GiftCertificateDTO;
import com.epam.esm.service.dto.TagConverter;
import com.epam.esm.service.util.ServiceValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

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

        ServiceValidator.giftCertificate(requestGiftCertificateDTO);

        ServiceValidator.listEmpty(requestGiftCertificateDTO.getTags());

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

        if (ServiceValidator.isListEmpty(createdGiftCertificateDTOs)) {

            throw new NoSuchElementException(getClass().getSimpleName() + " exception:GiftCertificate002");
        }

        addTagToDTO(createdGiftCertificateDTOs);

        return createdGiftCertificateDTOs;
    }

    @Override
    public GiftCertificateDTO searchGiftCertificate(int id) {

        ServiceValidator.isPositiveInteger(id);

        GiftCertificateDTO createdGiftCertificateDTO = GiftCertificateConverter
                .toDto(giftCertificateDAO.getGiftCertificate(id));

        if (ServiceValidator.stringNullOrEmpty(createdGiftCertificateDTO.getName())) {

            throw new NoSuchElementException(getClass().getSimpleName() + " exception:GiftCertificate003");
        }

        addTagToDTO(createdGiftCertificateDTO);

        return createdGiftCertificateDTO;
    }

    @Override
    public List<GiftCertificateDTO> searchGiftCertificates(Map<String, String> allRequestParams) {

        Map<String, String> parameters = ServiceValidator.createMapParameter(allRequestParams);

        ServiceValidator.mapEmpty(parameters);

        List<GiftCertificateDTO> createdGiftCertificateDTOs = GiftCertificateConverter
                .toDto(giftCertificateDAO.getGiftCertificates(parameters));

        if (ServiceValidator.isListEmpty(createdGiftCertificateDTOs)) {

            throw new NoSuchElementException(getClass().getSimpleName() + " exception:GiftCertificate004");
        }

        addTagToDTO(createdGiftCertificateDTOs);

        return createdGiftCertificateDTOs;
    }

    @Override
    public List<GiftCertificateDTO> getGiftCertificates(String tagName) {

        ServiceValidator.tagNameLengthValidator(tagName);

        List<GiftCertificateEntity> listGiftCertificates = giftCertificateDAO.getGiftCertificates(tagName);

        List<GiftCertificateDTO> createdGiftCertificateDTOs = GiftCertificateConverter.toDto(listGiftCertificates);

        if (ServiceValidator.isListEmpty(createdGiftCertificateDTOs)) {

            throw new NoSuchElementException(getClass().getSimpleName() + " exception:GiftCertificate005");
        }

        addTagToDTO(createdGiftCertificateDTOs);

        return createdGiftCertificateDTOs;
    }

    @Transactional
    @Override
    public GiftCertificateDTO updateGiftCertificateWithTags(GiftCertificateDTO requestGiftCertificateDTO) {

        ServiceValidator.giftCertificatePartialField(requestGiftCertificateDTO);

        GiftCertificateDTO updatedGiftCertificateDTO = updateGiftCertificate(requestGiftCertificateDTO);

        if (!ServiceValidator.isListEmpty(requestGiftCertificateDTO.getTags())) {

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

        ServiceValidator.isPositiveInteger(id);
        ServiceValidator.isZeroInteger(id);

        giftCertificateDAO.delGiftCertificate(id);
    }

    private void addTagToDTO(List<GiftCertificateDTO> giftCertificateDTOs) {

        giftCertificateDTOs.forEach(this::addTagToDTO);
    }

    private void addTagToDTO(GiftCertificateDTO giftCertificateDTO) {

        tagDAO.getListTag(giftCertificateDTO.getId())
                .forEach((tagEntity) -> giftCertificateDTO.getTags().add(TagConverter.toDto(tagEntity)));
    }

    private void checkTagNameAndBundleWithGiftCertificate(GiftCertificateDTO requestGiftCertificateDTO, int id) {

        requestGiftCertificateDTO.getTags().forEach((tagDTO) -> {

                    String tagName = tagDTO.getName();

                    if (!tagDAO.isTagExist(tagName)) {

                        tagDAO.createTag(tagName);
                    }

                    giftCertificateDAO.addGiftCertificateTag(id, tagName);
                }
        );
    }
}
