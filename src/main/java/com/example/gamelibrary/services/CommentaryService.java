package com.example.gamelibrary.services;

import com.example.gamelibrary.repositories.CommentaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentaryService {
    @Autowired
    private CommentaryRepository commentaryRepository;

    public void deleteById(Integer id) {
        commentaryRepository.deleteById(id);
    }
}
