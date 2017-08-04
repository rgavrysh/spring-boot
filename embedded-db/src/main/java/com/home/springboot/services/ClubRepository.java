package com.home.springboot.services;

import com.home.springboot.dao.Club;
import org.springframework.data.repository.Repository;

public interface ClubRepository extends Repository<Club, Long> {
    Club findByName(String name);
}
