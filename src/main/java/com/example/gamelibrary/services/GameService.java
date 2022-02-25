package com.example.gamelibrary.services;

import com.example.gamelibrary.models.Game;
import com.example.gamelibrary.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {
    @Autowired
    private GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game save(Game game) {
        return gameRepository.save(game);
    }

    public List<Game> getGames() {
        return gameRepository.findAll();
    }

    public Optional<Game> findById(int id) {
        return gameRepository.findById(id);
    }

    public Optional<Game> findByTitle(String title) {
        return gameRepository.findByTitleContaining(title);
    }

    public void delete(int id) {
        gameRepository.deleteById(id);
    }
}
