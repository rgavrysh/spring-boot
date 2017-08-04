package com.home.springboot.services;

import com.home.springboot.dao.Club;
import org.springframework.stereotype.Service;

@Service
public class ClubServiceImpl implements ClubService {
    private final ClubRepository clubRepository;

    public ClubServiceImpl(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    @Override
    public Club getClub(String name) {
        return this.clubRepository.findByName(name);
    }
}
