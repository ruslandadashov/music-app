package az.musicapp.services.impl;

import az.musicapp.domain.Genre;
import az.musicapp.exceptions.NotFoundException;
import az.musicapp.repositories.GenreRepository;
import az.musicapp.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    public Genre findGenreById(Long id) {

        return genreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Genre ID: %s does not exist", id)));
    }

    public Iterable<Genre> findAllGenres() {

        return genreRepository.findAll();
    }

    public Iterable<Genre> findGenresByName(String name) {

        return genreRepository.findByNameContainingIgnoreCase(name);
    }
}
