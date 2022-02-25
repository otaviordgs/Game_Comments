package com.example.gamelibrary.repositories;

import com.example.gamelibrary.models.Commentary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentaryRepository extends JpaRepository<Commentary, Integer> {
}