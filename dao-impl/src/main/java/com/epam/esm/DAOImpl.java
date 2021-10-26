package com.epam.esm;

import org.springframework.stereotype.Repository;

@Repository
public class DAOImpl implements DAO {

    @Override
    public String list() {

        return "in DAO";
    }
}
