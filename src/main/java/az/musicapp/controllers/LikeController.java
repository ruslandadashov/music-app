package az.musicapp.controllers;

import az.musicapp.domain.Song;
import az.musicapp.domain.User;
import az.musicapp.services.impl.LikeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeServiceImpl likeService;

    @GetMapping("/songs")
    public Iterable<Song> getAllLikedSongs(Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        return likeService.findAllLikedSongsByUser(user);
    }

    @PutMapping("/songs/{id}")
    public ResponseEntity<?> LikedSongById(@PathVariable Long id, Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        Song song = likeService.toggleLikedSong(id, user);
        return new ResponseEntity<>(song, HttpStatus.OK);
    }


}
