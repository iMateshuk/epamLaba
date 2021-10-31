package com.epam.esm.service;

import com.epam.esm.service.dto.GiftCertDTO;
import com.epam.esm.service.dto.GiftCertRequestDTO;

import java.util.List;

public interface GiftCertService {

    public void createGiftCert(GiftCertRequestDTO gcRequestDTO);

    public List<GiftCertDTO> searchGiftCerts();

    public GiftCertDTO searchGiftCert(int id);

    public void updateGiftCert(GiftCertRequestDTO gcRequestDTO);

    public void delGiftCert(int id);
}
