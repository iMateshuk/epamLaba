package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertDAO;
import com.epam.esm.service.GiftCertService;
import com.epam.esm.service.dto.ConvertEntityToGiftCertDTO;
import com.epam.esm.service.dto.ConvertGiftCertDTOToEntity;
import com.epam.esm.service.dto.GiftCertDTO;
import com.epam.esm.service.dto.GiftCertRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiftCertImpl implements GiftCertService {

    @Autowired
    GiftCertDAO gcDAO;

    @Override
    public void createGiftCert(GiftCertRequestDTO gcRequestDTO) {

        gcDAO.createGiftCert(ConvertGiftCertDTOToEntity.getGiftCertEntity(gcRequestDTO));
    }

    @Override
    public List<GiftCertDTO> searchGiftCerts() {

        return ConvertEntityToGiftCertDTO.getGiftCertDTO(gcDAO.searchGiftCerts());
    }

    @Override
    public GiftCertDTO searchGiftCert(int id) {

        return ConvertEntityToGiftCertDTO.getGiftCertDTO(gcDAO.searchGiftCert(id));
    }

    @Override
    public void updateGiftCert(GiftCertRequestDTO gcRequestDTO) {

        gcDAO.updateGiftCert(ConvertGiftCertDTOToEntity.getGiftCertEntity(gcRequestDTO));
    }

    @Override
    public void delGiftCert(int id) {

        gcDAO.delGiftCert(id);
    }
}
