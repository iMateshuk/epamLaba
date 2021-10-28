package com.epam.esm.service;

import com.epam.esm.service.dto.GiftCertDTO;

import java.util.List;

public interface GCService {

    public String list();

    public List<GiftCertDTO> listGift();

    public GiftCertDTO getGiftCert(int id);
}
