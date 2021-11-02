package az.musicapp.services.impl;

import az.musicapp.domain.Album;
import az.musicapp.exceptions.NotFoundException;
import az.musicapp.repositories.AlbumRepository;
import az.musicapp.services.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;

    public Album findAlbumById(Long id) {

        return albumRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Album ID: %s does not exist", id)));
    }

    public Iterable<Album> findAllAlbums() {

        return albumRepository.findAll();
    }

    public Iterable<Album> findAlbumsByName(String name) {

        return albumRepository.findByNameContainingIgnoreCase(name);
    }
}
