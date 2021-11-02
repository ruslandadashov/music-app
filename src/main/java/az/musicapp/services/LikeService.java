package az.musicapp.services;

import az.musicapp.domain.Song;
import az.musicapp.domain.User;

public interface LikeService {

     Iterable<Song> findAllLikedSongsByUser(User user);

     Song toggleLikedSong(Long id, User user) ;

     User updateUser(User user);

}
