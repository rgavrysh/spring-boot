package com.home.springboot.services;

import com.home.springboot.dao.Article;
import com.home.springboot.repository.ArticleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public Article save(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public Article findOne(String id) {
        return articleRepository.findOne(id);
    }

    @Override
    public Iterable<Article> findAll() {
        return articleRepository.findAll();
    }

    @Override
    public List<Article> findByTags(String tags) {
        return articleRepository.findAllByTags(tags);
    }

    @Override
    public Page<Article> findByAuthorName(String name, Pageable pageable) {
        return articleRepository.findByAuthorsName(name, pageable);
    }

    @Override
    public Page<Article> findByAuthorNameUsingCustomQuery(String name, Pageable pageable) {
        return articleRepository.findByAuthorsNameUsingCustomQuery(name, pageable);
    }

    @Override
    public long count() {
        return articleRepository.count();
    }

    @Override
    public void delete(Article article) {
        articleRepository.delete(article);
    }
}
