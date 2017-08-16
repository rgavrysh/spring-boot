package com.home.springboot;

import com.home.springboot.model.Player;
import com.home.springboot.repos.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@SpringBootApplication
@RestController
public class EmbeddedDbController {
    @Autowired
    private PlayerRepository playerRepository;

    @GetMapping(value = "/")
    String init() {
        return "Init page.";
    }

    @PostMapping("/mongo/player/create/{name}")
    public Player savePlayer(@PathVariable final String name) {
        Player player = new Player();
        player.setName(name);
        player.setPosition("FW");
        player.setId(UUID.randomUUID().toString());
        return playerRepository.save(player);
    }

    @GetMapping("/mongo/player")
    public Iterable<Player> getPlayer() {
        return playerRepository.findAll();
    }

    public static void main(String[] args) {
        SpringApplication.run(EmbeddedDbController.class, args);
    }
}