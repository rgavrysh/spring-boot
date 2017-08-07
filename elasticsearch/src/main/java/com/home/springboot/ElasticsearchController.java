package com.home.springboot;

import com.home.springboot.dao.Article;
import com.home.springboot.dao.Author;
import com.home.springboot.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = {"com.home.springboot.repository"})
public class ElasticsearchController {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private ArticleService articleService;

    @RequestMapping("/")
    String init() {
        return "Init page.";
    }

    @RequestMapping(value = "/article/create", method = RequestMethod.POST)
    @ResponseBody
    public Article addArticle() {
        Article article = new Article("Be smart");
        article.setTags(new String[]{"inspiration"});
        final Author roman = new Author("Roman");
        roman.setId("1");
        article.setAuthors(Arrays.asList(roman));
        elasticsearchTemplate.createIndex(Article.class);
        article = articleService.save(article);
        return article;
    }

    @RequestMapping("/article/{author}")
    @ResponseBody
    public List<Article> getArticle(@PathVariable("author") final String author) throws Exception {
        if (elasticsearchTemplate.indexExists(Article.class)) {
            return this.articleService.findByAuthorName(author, new PageRequest(0, 5)).getContent();
        }
        throw new Exception("Index for " + Article.class.getCanonicalName() + " not exist");
    }

    @RequestMapping("/article/{author}/title")
    @ResponseBody
    public String getTitleByAuthor(@PathVariable("author") final String author) {
        List<Article> articles = this.articleService.findByAuthorName(author, new PageRequest(0, 5)).getContent();
        if (!articles.isEmpty()) {
            return articles.get(0).getTitle();
        }
        return "No articles by author: " + author;
    }

    public static void main(String[] args) {
        SpringApplication.run(ElasticsearchController.class, args);

    }
}
