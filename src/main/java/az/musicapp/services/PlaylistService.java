package az.musicapp.services;

import az.musicapp.domain.Playlist;
import az.musicapp.domain.User;

public interface PlaylistService {

     Playlist findPlaylistByIdAndUser(Long id, User user) ;

     Iterable<Playlist> findAllPlaylistsByUser(User user) ;


     Iterable<Playlist> findPlaylistsByNameAndUser(String name, User user);

     Playlist savePlaylist(String name, User user);

     Playlist updatePlaylistById(String name, Long id, User user);


     void deletePlaylistById(Long id, User user) ;

     Playlist addSongById(Long playlistId, Long songId, User user);

     void removeSongById(Long playlistId, Long songId, User user);
}
