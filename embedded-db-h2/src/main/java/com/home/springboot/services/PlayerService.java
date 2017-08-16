package com.home.springboot.services;


import com.home.springboot.dao.Player;

public interface PlayerService {
    Player getPlayer(String name);

    Player addPlayer(Player player);

    long getTotalNumberOfPlayers();
}
