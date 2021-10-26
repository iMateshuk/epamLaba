package com.epam.esm.dao.impl;

import com.epam.esm.dao.DAO;
import com.epam.esm.dao.util.ConnectionPool;
import com.epam.esm.dao.util.QuerySQL;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class DAOImpl implements DAO {

    @Override
    public String list() {

        final String sql = QuerySQL.SQL_GC_SELECT_ALL.getSQL();

        String answer = "in DAO";

        try (Connection con = ConnectionPool.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                answer = rs.getString(QuerySQL.SQL_GC_COLUMN_NAME.getSQL()) + " : " + rs.getString(QuerySQL.SQL_GC_COLUMN_DESC.getSQL());
                System.out.println(answer);
            }

        } catch (SQLException e) {

            //throw new DAOException("Can't (sql) :: gclist", e);
        }

        return answer;
    }
}
