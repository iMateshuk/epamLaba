package com.epam.esm.service.impl;

import com.epam.esm.dao.DAO;
import com.epam.esm.service.GCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceImpl implements GCService {

    @Autowired
    DAO dao;

    @Override
    public String list() {

        return dao.list();
    }
}
