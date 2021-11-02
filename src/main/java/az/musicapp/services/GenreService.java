package az.musicapp.services;

import az.musicapp.domain.Genre;

public interface GenreService {

     Genre findGenreById(Long id);

     Iterable<Genre> findAllGenres() ;

     Iterable<Genre> findGenresByName(String name) ;
}
