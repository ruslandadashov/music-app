package az.musicapp.services.impl;

import az.musicapp.domain.Playlist;
import az.musicapp.domain.Song;
import az.musicapp.domain.User;
import az.musicapp.exceptions.AlreadyExistsException;
import az.musicapp.exceptions.NotFoundException;
import az.musicapp.exceptions.UnauthorizedUserException;
import az.musicapp.repositories.PlaylistRepository;
import az.musicapp.services.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {

    private final UserServiceImpl userService;
    private final SongServiceImpl songService;
    private final PlaylistRepository playlistRepository;

    public Playlist findPlaylistByIdAndUser(Long id, User user) {

        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Playlist ID: %s does not exist", id)));

        if (!playlist.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedUserException(String.format("User: %s is not authorized to access playlist ID: %s",
                    user.getUsername(), id));
        }

        return playlist;
    }

    public Iterable<Playlist> findAllPlaylistsByUser(User user) {


        user = userService.findUserById(user.getId());
        return user.getPlaylists();
    }

    @Transactional
    public Iterable<Playlist> findPlaylistsByNameAndUser(String name, User user) {

        user = userService.findUserById(user.getId());


        Predicate<Playlist> byContainsName = playlist -> playlist.getName().toLowerCase()
                .contains(name.toLowerCase());

        return user.getPlaylists()
                .stream()
                .filter(byContainsName)
                .collect(Collectors.toList());
    }

    public Playlist savePlaylist(String name, User user) {

        user = userService.findUserById(user.getId());

        Playlist playlist = new Playlist();
        playlist.setName(name);
        playlist.setUser(user);

        return playlistRepository.save(playlist);
    }

    public Playlist updatePlaylistById(String name, Long id, User user) {

        user = userService.findUserById(user.getId());

        Playlist playlist = findPlaylistByIdAndUser(id, user);
        playlist.setName(name);

        return playlistRepository.save(playlist);
    }


    public void deletePlaylistById(Long id, User user) {

        user = userService.findUserById(user.getId());

        Playlist playlist = findPlaylistByIdAndUser(id, user);

        playlistRepository.deleteById(playlist.getId());
    }

    public Playlist addSongById(Long playlistId, Long songId, User user) {

        user = userService.findUserById(user.getId());
        Playlist playlist = findPlaylistByIdAndUser(playlistId, user);

        Song song = songService.findSongById(songId);
        if (playlist.getSongs().contains(song)) {
            throw new AlreadyExistsException(String.format("Song: %s already exists in playlist: %s",
                    song.getName(), playlist.getName()));
        }
        playlist.addSong(song);

        return playlistRepository.save(playlist);
    }

    public void removeSongById(Long playlistId, Long songId, User user) {

        user = userService.findUserById(user.getId());
        Playlist playlist = findPlaylistByIdAndUser(playlistId, user);

        Song song = songService.findSongById(songId);
        if (!playlist.getSongs().contains(song)) {
            throw new NotFoundException(String.format("Song: %s not found in playlist: %s",
                    song.getName(), playlist.getName()));
        }

         playlistRepository.deleteSongById(song.getId());
    }
}