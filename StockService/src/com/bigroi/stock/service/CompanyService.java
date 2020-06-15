package com.bigroi.stock.service;

import com.bigroi.stock.bean.db.Company;

import java.util.List;

public interface CompanyService {

    void changeStatusCompany(long id);

    List<Company> getAllCompanies();

    Company getCompanyById(long id);

    Company getByName(String name);

    Company getByRegNumber(String regNumber);
}
