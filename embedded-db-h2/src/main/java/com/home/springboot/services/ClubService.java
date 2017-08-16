package com.home.springboot.services;

import com.home.springboot.dao.Club;

public interface ClubService {
    Club getClub(String name);

    long getTotalNumberOfClubs();
}
