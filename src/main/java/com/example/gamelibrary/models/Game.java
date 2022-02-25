package com.example.gamelibrary.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "studio", nullable = false, length = 100)
    private String studio;

    @Column(name = "released", nullable = false)
    private LocalDate released;

    @Column(name = "price", nullable = false)
    private Double price;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "game_fk", referencedColumnName = "id")
    private List<Commentary> commentaries = new ArrayList<>();

    public Game() {
    }

    public Game(String title, String studio, LocalDate released, Double price) {
        this.title = title;
        this.studio = studio;
        this.released = released;
        this.price = price;
    }

    public List<Commentary> getCommentaries() {
        return commentaries;
    }

    public void setCommentaries(List<Commentary> commentaries) {
        this.commentaries = commentaries;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDate getReleased() {
        return released;
    }

    public void setReleased(LocalDate released) {
        this.released = released;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}