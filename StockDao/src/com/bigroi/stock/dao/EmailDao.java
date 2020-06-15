package com.bigroi.stock.dao;

import com.bigroi.stock.bean.db.Email;

import java.util.List;

public interface EmailDao {

    List<Email> getAll();

    void add(Email email);

    boolean deleteById(long id);

}
