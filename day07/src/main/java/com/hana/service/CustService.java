package com.hana.service;

import com.hana.data.CustDto;
import com.hana.exception.DuplicatedIdException;
import com.hana.exception.NotFoundException;
import com.hana.frame.ConnectionPool;
import com.hana.frame.Service;
import com.hana.repository.CustRepository;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class CustService implements Service<String, CustDto> {

    CustRepository repository;
    ConnectionPool cp;
    public CustService(){
        repository = new CustRepository();
        try {
            cp = ConnectionPool.create();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public CustDto add(CustDto custDto) throws DuplicatedIdException, Exception {
        Connection con = cp.getConnection();
        try {
            con.setAutoCommit(false);
            repository.insert(custDto, con);
            con.commit();
        } catch (Exception e) {
            con.rollback();
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        return null;
    }

    @Override
    public CustDto modify(CustDto custDto) throws NotFoundException, Exception {
        Connection con = cp.getConnection();
        try {
            con.setAutoCommit(false);
            repository.update(custDto, con);
            con.commit();
        } catch (Exception e) {
            con.rollback();
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        return null;
    }

    @Override
    public Boolean remove(String s) throws NotFoundException, Exception {
        Connection con = cp.getConnection();
        try {
            con.setAutoCommit(false);
            repository.delete(s, con);
            con.commit();
        } catch (Exception e) {
            con.rollback();
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        return null;
    }

    @Override
    public List<CustDto> get() throws Exception {
        Connection con = cp.getConnection();
        List<CustDto> result = null;
        try {
            con.setAutoCommit(false);
            result = repository.select(con);
            con.commit();
        } catch (Exception e) {
            con.rollback();
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        return result;
    }

    @Override
    public CustDto get(String s) throws NotFoundException, Exception {
        Connection con = cp.getConnection();
        CustDto result = null;
        try {
            con.setAutoCommit(false);
            result = repository.select(s, con);
            con.commit();
        } catch (Exception e) {
            con.rollback();
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        return result;
    }
}
