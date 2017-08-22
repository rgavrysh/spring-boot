package com.home.pses.services;

import com.home.pses.entity.Company;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CompanyService {

    List<Company> findAll();

    Company findOne(int id);

    Company save(Company company);

    void delete(Company company);

    Page<Company> findAllFromES();

    Company saveToEs(Company company);
 }
