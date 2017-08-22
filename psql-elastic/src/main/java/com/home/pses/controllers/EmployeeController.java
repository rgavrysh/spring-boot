package com.home.pses.controllers;

import com.home.pses.entity.Company;
import com.home.pses.services.CompanyService;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @GetMapping("/id/{id}")
    @ResponseBody
    public Company init(@PathVariable("id") final int id) {
        return companyService.findOne(id);
    }

    @GetMapping("/all")
    @ResponseBody
    public List<Company> getAllUsingES() {
        return companyService.findAllFromES().getContent();
    }

    @GetMapping("/name/{name}")
    @ResponseBody
    public List<Company> searchCompanyByName(@PathVariable("name") final String name) {
        final String regexp = name.toLowerCase() + ".*";
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.regexpQuery("name", regexp))
                .build();
        List<Company> companies = elasticsearchTemplate.queryForList(query, Company.class);
        return companies;
    }

}
