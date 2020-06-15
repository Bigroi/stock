package com.bigroi.stock.dao;

import com.bigroi.stock.bean.common.CompanyStatus;
import com.bigroi.stock.bean.db.Company;

import java.util.List;

public interface CompanyDao {

    Company getById(long id);

    void add(Company company);

    boolean update(Company company);

    List<Company> getAllCompany();

    void setStatus(long companyId, CompanyStatus status);

    Company getByRegNumber(String regNumber);

    Company getByName(String name);

}
