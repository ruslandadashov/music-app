package az.musicapp.services.impl;

import az.musicapp.domain.Song;
import az.musicapp.domain.User;
import az.musicapp.services.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeServiceImpl implements LikeService {

    private final UserServiceImpl userService;
    private final SongServiceImpl songService;

    public Iterable<Song> findAllLikedSongsByUser(User user) {

        user = userService.findUserById(user.getId());
        return user.getLikedSongs();
    }

    public Song toggleLikedSong(Long id, User user) {

        user = userService.findUserById(user.getId());
        Song song = songService.findSongById(id);

        if (user.getLikedSongs().contains(song)) {
            user.removeSong(song);
        } else {
            user.addSong(song);
        }

        userService.updateUser(user);
        return song;
    }

    public User updateUser(User user) {

        return userService.updateUser(user);
    }

}
