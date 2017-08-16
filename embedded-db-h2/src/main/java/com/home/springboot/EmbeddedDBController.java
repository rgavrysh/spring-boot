package com.home.springboot;

import com.home.springboot.dao.Club;
import com.home.springboot.dao.Player;
import com.home.springboot.services.ClubService;
import com.home.springboot.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@SpringBootApplication
public class EmbeddedDBController {
    @Autowired
    private PlayerService playerService;
    @Autowired
    private ClubService clubService;

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

    public static void main(String[] args) {
        SpringApplication.run(EmbeddedDBController.class, args);
    }
}
