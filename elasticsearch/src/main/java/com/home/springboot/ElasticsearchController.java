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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@SpringBootApplication
//@EnableElasticsearchRepositories(basePackages = {"com.home.springboot.repository"})
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
    public Article addArticle() {
        Article article = new Article("Be smart");
        article.setTags(new String[]{"inspiration"});
        article.setAuthors(Arrays.asList(new Author("Roman")));
        Article savedArticle = articleService.save(article);
        return savedArticle;
    }

    @RequestMapping("/article/{author}")
    List<Article> getArticle(@PathVariable("author") final String author) {
        return this.articleService.findByAuthorName(author, new PageRequest(0,10))
                .getContent();
    }

    @RequestMapping("/article/{author}/title")
    String getTitleByAuthor(@PathVariable("author") final String author) {
        List<Article> articleList = this.articleService.findByAuthorName(author, new PageRequest(0, 1))
                .getContent();
        if (!articleList.isEmpty()) {
            return articleList.get(0).getTitle();
        }
        return "No articles of this author";
    }

    public static void main(String[] args) {
        SpringApplication.run(ElasticsearchController.class, args);
    }
}
