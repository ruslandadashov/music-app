package az.musicapp.services;

import az.musicapp.domain.Song;

public interface SongService {
     Song findSongById(Long id) ;

     Iterable<Song> findAllSongs() ;

     Iterable<Song> findSongsByName(String name) ;
}
