package com.home.springboot.services;

import com.home.springboot.dao.Book;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private EntityManager entityManager;

    public List<Book> findBookByName(String name) {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Book.class).get();
        Query query = queryBuilder.keyword().onFields("title", "subtitle", "author.name")
                .matching(name)
                .createQuery();
        javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(query, Book.class);
        return jpaQuery.getResultList();
    }

    public Book findOne(Integer id) {
        return bookRepository.findOne(id);
    }


}
