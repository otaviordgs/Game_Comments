package com.example.gamelibrary.controllers;

import com.example.gamelibrary.models.Commentary;
import com.example.gamelibrary.models.Game;
import com.example.gamelibrary.services.CommentaryService;
import com.example.gamelibrary.services.GameService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/games")
public class GameController {
    private GameService gameService;
    @Autowired
    private CommentaryService commentaryService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public List<Game> getAllGames(){
        return gameService.getGames();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getGameById(@PathVariable int id){
        Optional<Game> game = gameService.findById(id);
        if(game.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não existe");
        return  ResponseEntity.status(HttpStatus.FOUND).body(game.get());
    }

    @GetMapping("/search")
    public ResponseEntity<Object> getGameByTitle(@RequestParam(name = "title") String title){
        Optional<Game> game = gameService.findByTitle(title);
        if(game.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Título do jogo não existe");
        return  ResponseEntity.status(HttpStatus.FOUND).body(game.get());
    }

    @GetMapping("/search/{year}")
    public List<Game> getByYearRelease(@PathVariable int year){
        List<Game> games = getAllGames();
        List<Game> gamesByYear = new ArrayList<>();
        for(Game g:games){
            if(g.getReleased().getYear() == year)
                gamesByYear.add(g);
        }
        return gamesByYear;
    }

    @PostMapping
    public Game createGame(@RequestBody Game game){
        return gameService.save(game);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateGame(@PathVariable int id,@RequestBody Game game){
        Optional<Game> gameOptional = gameService.findById(id);
        if(gameOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não existe");
        int saveId= gameOptional.get().getId();
        BeanUtils.copyProperties(game, gameOptional.get());
        gameOptional.get().setId(saveId);
        return ResponseEntity.status(HttpStatus.OK).body(gameService.save(gameOptional.get()));
    }

    @PutMapping("{id}/comment")
    public ResponseEntity<Object> newCommentary(@PathVariable int id,
                                                @RequestBody Commentary commentary){
        Optional<Game> gameOptional = gameService.findById(id);
        if(gameOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não existe");
        gameOptional.get().getCommentaries().add(commentary);
        return ResponseEntity.status(HttpStatus.CREATED).body(gameService.save(gameOptional.get()));
    }

    @PutMapping("/{id}/comment/{numeroComentario}")
    public ResponseEntity<Object> updateCommentary(@PathVariable int id,
                                                   @PathVariable int numeroComentario
                                                   ,@RequestBody Commentary commentary){
        Optional<Game> gameOptional = gameService.findById(id);
        if(gameOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id do jogo não existe");
        //primeiro comentário é o 0, porem vou deixar como se fosse o 1 mesmo
        int saveId= gameOptional.get().getCommentaries().get(numeroComentario-1).getId();
        BeanUtils.copyProperties(commentary, gameOptional.get().getCommentaries().get(numeroComentario-1));
        gameOptional.get().getCommentaries().get(numeroComentario-1).setId(saveId);
        return ResponseEntity.status(HttpStatus.OK).body(gameService.save(gameOptional.get()));
    }


    @DeleteMapping("/{id}/comment/{numeroComentario}")
    public ResponseEntity<Object> deleteCommentary(@PathVariable int id,
                                                   @PathVariable int numeroComentario){
        Optional<Game> gameOptional = gameService.findById(id);
        if(gameOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id do jogo não existe");
        //primeiro comentário é o 0, porem vou deixar como se fosse o 1 mesmo
        gameOptional.get().getCommentaries().remove(numeroComentario-1);
        gameService.save(gameOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Comentário apagado com sucesso!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteGameById(@PathVariable int id){
        Optional<Game> game = gameService.findById(id);
        if(game.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não existe");
        gameService.delete(id);
        return  ResponseEntity.status(HttpStatus.FOUND).body("Jogo apagado com sucesso!");
    }


}
