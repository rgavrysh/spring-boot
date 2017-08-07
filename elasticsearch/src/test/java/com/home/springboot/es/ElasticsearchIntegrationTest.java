package com.home.springboot.es;

import com.home.springboot.ElasticsearchController;
import com.home.springboot.dao.Article;
import com.home.springboot.dao.Author;
import com.home.springboot.services.ArticleService;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ElasticsearchController.class)
public class ElasticsearchIntegrationTest {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    private final Author jim = new Author("Jim");
    private final Author rohn = new Author("Rohn");

    @Before
    public void before() {
        elasticsearchTemplate.deleteIndex(Article.class);
        elasticsearchTemplate.createIndex(Article.class);

        Article article = new Article("Spring Data Elasticsearch");
        article.setAuthors(Arrays.asList(jim, rohn));
        article.setTags("inspiration");
        articleService.save(article);

        Article search_engine = new Article("Search engine");
        search_engine.setAuthors(Arrays.asList(jim));
        search_engine.setTags("technical");
        articleService.save(search_engine);
    }

    @Test
    public void givenArticleService_whenSaveArticle_thenIdIsAssigned() {
        Article temp_article = new Article("Temp Article");
        temp_article.setAuthors(Arrays.asList(rohn));
        articleService.save(temp_article);
        Assert.assertNotNull(temp_article.getId());
    }

    @Test
    public void givenPersistedArticle_whenSearchByAuthorsName_thenRightFound() {
        final Page<Article> byAuthorName = articleService.findByAuthorName(jim.getName(),
                new PageRequest(0, 5));
        Assert.assertEquals(2l, byAuthorName.getTotalElements());
    }

    @Test
    public void givenPersistedArticle_whenSearchByTag_thenRightFound() {
        final List<Article> technicalArticles = articleService.findByTags("technical");
        Assert.assertEquals(1l, technicalArticles.size());
    }

    @Test
    public void givenPersistedArticle_whenSearchByCustomQuery_thenRightArticleFound() {
        final Page<Article> byAuthorName = articleService.findByAuthorNameUsingCustomQuery(rohn.getName(),
                new PageRequest(0, 5));

        Assert.assertEquals(1l, byAuthorName.getTotalElements());
    }

    @Test
    public void givenPersistedArticle_whenUseRegexpQuery_thenRightArticlesFound() {
        final NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withFilter(QueryBuilders.regexpQuery("title", ".*engine.*"))
                .build();
        final List<Article> articles = elasticsearchTemplate.queryForList(searchQuery, Article.class);
        Assert.assertEquals(1l, articles.size());
    }

    @Test
    public void givenSaveDoc_whenTitleUpdated_thenCouldFindUpdatedTitle() {
        final NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.fuzzyQuery("title", "search"))
                .build();
        final List<Article> articles = elasticsearchTemplate.queryForList(searchQuery, Article.class);
        Assert.assertEquals(1l, articles.size());
        final Article article = articles.get(0);
        final String newTitle = "Getting started with search engine";
        article.setTitle(newTitle);
        articleService.save(article);
        Assert.assertEquals(newTitle, articleService.findOne(article.getId()).getTitle());
    }

//    @Test
    public void givenFullTitle_whenQueryOnVerbatimField_thenDocIsFound() {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("title.verbatim", "Search engine"))
                .build();

        List<Article> articles = elasticsearchTemplate.queryForList(searchQuery, Article.class);
        Assert.assertEquals(1l, articles.size());
        searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("title.verbatim", "Search"))
                .build();
        articles = elasticsearchTemplate.queryForList(searchQuery, Article.class);
        Assert.assertEquals(0l, articles.size());
    }

    @Test
    public void givenSaveDoc_whenDelete_thenRemovedFromIndex() {
        final String articleTitle = "Spring Data Elasticsearch";

        final NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("title", articleTitle).minimumShouldMatch("75%"))
                .build();
        final List<Article> articles = elasticsearchTemplate.queryForList(searchQuery, Article.class);
        Assert.assertEquals(1l, articles.size());
        final long count = articleService.count();
        articleService.delete(articles.get(0));
        Assert.assertEquals(count - 1, articleService.count());
    }
}
