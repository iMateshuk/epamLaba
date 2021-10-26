package com.epam.esm.dao.impl;

import com.epam.esm.dao.DAO;
import org.springframework.stereotype.Repository;

@Repository
public class DAOImpl implements DAO {

    @Override
    public String list() {

        return "in DAO";
    }
}
