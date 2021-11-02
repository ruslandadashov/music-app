package az.musicapp.services.impl;

import az.musicapp.domain.Artist;
import az.musicapp.exceptions.NotFoundException;
import az.musicapp.repositories.ArtistRepository;
import az.musicapp.services.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;

    public Artist findArtistById(Long id) {

        return artistRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Artist ID: %s does not exist", id)));
    }

    public Iterable<Artist> findAllArtists() {

        return artistRepository.findAll();
    }

    public Iterable<Artist> findArtistsByName(String name) {

        return artistRepository.findByNameContainingIgnoreCase(name);
    }
}
