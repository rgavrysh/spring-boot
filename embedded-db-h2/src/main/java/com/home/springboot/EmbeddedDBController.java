package com.home.springboot;

import com.home.springboot.dao.Book;
import com.home.springboot.dao.Club;
import com.home.springboot.dao.Player;
import com.home.springboot.services.BookService;
import com.home.springboot.services.ClubService;
import com.home.springboot.services.PlayerService;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import java.util.List;


@RestController
@SpringBootApplication
public class EmbeddedDBController {
    @Autowired
    private PlayerService playerService;
    @Autowired
    private ClubService clubService;
    @Autowired
    private BookService bookService;
    @Autowired
    private EntityManager entityManager;

    @Value("${do.reindex}")
    private Boolean reindex = false;

    @RequestMapping("/")
    String init() {
        return "Init page.";
    }

    @RequestMapping("/player/{name}")
    Player getPlayer(@PathVariable("name") final String name) {
        return this.playerService.getPlayer(name);
    }

    @RequestMapping("/club/{name}")
    Club getClub(@PathVariable("name") final String name) {
        return this.clubService.getClub(name);
    }

    @RequestMapping("/book")
    List<Book> findBook(@RequestParam("name") String name) {

        if (reindex) {
            FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
            try {
                fullTextEntityManager.createIndexer().startAndWait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return bookService.findBookByName(name);
    }

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(EmbeddedDBController.class, args);
    }
}
