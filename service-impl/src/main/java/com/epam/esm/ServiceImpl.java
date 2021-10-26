package com.epam.esm;

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
