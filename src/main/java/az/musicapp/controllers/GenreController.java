package az.musicapp.controllers;

import az.musicapp.domain.Genre;
import az.musicapp.services.impl.GenreServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/genres")
@RequiredArgsConstructor
public class GenreController {

    private final GenreServiceImpl genreService;

    @GetMapping("")
    public Iterable<Genre> getAllGenres() {

        return genreService.findAllGenres();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGenreById(@PathVariable Long id) {

        Genre genre = genreService.findGenreById(id);
        return new ResponseEntity<>(genre, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public Iterable<Genre> getGenresByName(@PathVariable String name) {

        return genreService.findGenresByName(name);
    }
}
