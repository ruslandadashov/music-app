package az.musicapp.services;

import az.musicapp.domain.Artist;

public interface ArtistService {

     Artist findArtistById(Long id) ;

     Iterable<Artist> findAllArtists() ;

     Iterable<Artist> findArtistsByName(String name) ;
}
