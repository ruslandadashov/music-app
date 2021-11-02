package az.musicapp.controllers;

import az.musicapp.domain.Song;
import az.musicapp.services.impl.SongServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/songs")
@RequiredArgsConstructor
public class SongController {

    private final SongServiceImpl songService;

    @GetMapping("")
    public Iterable<Song> getAllSongs() {

        return songService.findAllSongs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSongById(@PathVariable Long id) {

        Song song = songService.findSongById(id);
        return new ResponseEntity<>(song, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public Iterable<Song> getSongsByName(@PathVariable String name) {

        return songService.findSongsByName(name);
    }
}
