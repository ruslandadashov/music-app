package az.musicapp.controllers;

import az.musicapp.domain.Playlist;
import az.musicapp.domain.User;
import az.musicapp.payload.requests.PlaylistNameRequest;
import az.musicapp.services.impl.PlaylistServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("api/playlists")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistServiceImpl playlistService;

    @GetMapping("")
    public Iterable<Playlist> getAllPlaylists(Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        return playlistService.findAllPlaylistsByUser(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPlaylistById(@PathVariable Long id, Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        Playlist playlist = playlistService.findPlaylistByIdAndUser(id, user);
        return new ResponseEntity<>(playlist, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public Iterable<Playlist> getPlaylistsByName(@PathVariable String name, Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        return playlistService.findPlaylistsByNameAndUser(name, user);
    }

    @PostMapping("")
    public ResponseEntity<?> createPlaylistById(@Valid @RequestBody PlaylistNameRequest playlistNameRequest,
                                                Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        Playlist playlist = playlistService.savePlaylist(playlistNameRequest.getName(), user);
        return new ResponseEntity<>(playlist, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePlaylistById(@PathVariable Long id, @Valid @RequestBody PlaylistNameRequest playlistNameRequest,
                                                Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        Playlist playlist = playlistService.updatePlaylistById(playlistNameRequest.getName(), id, user);
        return new ResponseEntity<>(playlist, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlaylistById(@PathVariable Long id, Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        playlistService.deletePlaylistById(id, user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{playlistId}/song/{songId}")
    public ResponseEntity<?> addSongToPlaylistById(@PathVariable Long playlistId, @PathVariable Long songId,
                                                   Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        Playlist playlist = playlistService.addSongById(playlistId, songId, user);
        return new ResponseEntity<>(playlist, HttpStatus.OK);
    }

    @DeleteMapping("/{playlistId}/song/{songId}")
    public ResponseEntity<?> removeSongFromPlaylistById(@PathVariable Long playlistId, @PathVariable Long songId,
                                                        Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        playlistService.removeSongById(playlistId, songId, user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
