package com.home.pses.services;

import com.home.pses.entity.Company;
import com.home.pses.repositories.CompanyRepository;
import com.home.pses.search.CompanySearchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanySearchRepository companySearchRepository;

    public CompanyServiceImpl(CompanyRepository repository, CompanySearchRepository searchRepository) {
        this.companyRepository = repository;
        this.companySearchRepository = searchRepository;
    }


    @Override
    public List<Company> findAll() {
        return (List<Company>) this.companyRepository.findAll();
    }

    public Page<Company> findAllFromES() {
        return this.companySearchRepository.findAll(new PageRequest(0, 5));
    }

    public Company saveToEs(Company company) {
        return this.companySearchRepository.save(company);
    }

    @Override
    public Company findOne(int id) {
        return this.companyRepository.findOne(id);
    }

    @Override
    public Company save(Company company) {
        return this.companyRepository.save(company);
    }

    @Override
    public void delete(Company company) {
        this.companyRepository.delete(company);
    }
}
