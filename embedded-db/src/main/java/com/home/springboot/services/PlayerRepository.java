package com.home.springboot.services;

import com.home.springboot.dao.Player;
import org.springframework.data.repository.Repository;

public interface PlayerRepository extends Repository<Player, Long> {

    Player findByName(String name);

    Player save(Player player);
}
