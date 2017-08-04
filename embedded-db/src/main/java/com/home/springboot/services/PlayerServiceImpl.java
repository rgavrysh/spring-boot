package com.home.springboot.services;

import com.home.springboot.dao.Player;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Player getPlayer(String name) {
        Assert.hasLength(name, "Name must not be empty.");
        return this.playerRepository.findByName(name);
    }

    @Override
    public Player addPlayer(Player player) {
        return this.playerRepository.save(player);
    }
}
