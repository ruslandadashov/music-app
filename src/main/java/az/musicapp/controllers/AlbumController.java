package az.musicapp.controllers;

import az.musicapp.domain.Album;
import az.musicapp.services.impl.AlbumServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/albums")
@RequiredArgsConstructor
public class AlbumController {
    private final AlbumServiceImpl albumService;

    @GetMapping("")
    public Iterable<Album> getAllAlbums() {

        return albumService.findAllAlbums();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAlbumById(@PathVariable Long id) {

        Album album = albumService.findAlbumById(id);
        return new ResponseEntity<>(album, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public Iterable<Album> getAlbumsByName(@PathVariable String name) {

        return albumService.findAlbumsByName(name);
    }
}
