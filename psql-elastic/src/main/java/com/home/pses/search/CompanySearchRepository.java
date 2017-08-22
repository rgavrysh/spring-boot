package com.home.pses.search;

import com.home.pses.entity.Company;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanySearchRepository extends ElasticsearchRepository<Company, Integer> {
}
