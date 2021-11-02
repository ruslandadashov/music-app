package az.musicapp.controllers;

import az.musicapp.domain.Artist;
import az.musicapp.services.impl.ArtistServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/artists")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistServiceImpl artistService;

    @GetMapping("")
    public Iterable<Artist> getAllArtists() {

        return artistService.findAllArtists();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getArtistById(@PathVariable Long id) {

        Artist artist = artistService.findArtistById(id);
        return new ResponseEntity<>(artist, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public Iterable<Artist> getArtistsByName(@PathVariable String name) {

        return artistService.findArtistsByName(name);
    }
}
