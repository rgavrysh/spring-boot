package com.home.springboot.repos;

import com.home.springboot.model.Player;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlayerRepository extends MongoRepository<Player, String>{
}
