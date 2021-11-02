package az.musicapp.services.impl;

import az.musicapp.domain.Song;
import az.musicapp.exceptions.NotFoundException;
import az.musicapp.repositories.SongRepository;
import az.musicapp.services.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;

    public Song findSongById(Long id) {

        return songRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Song ID: %s does not exist", id)));
    }

    public Iterable<Song> findAllSongs() {

        return songRepository.findAll();
    }

    public Iterable<Song> findSongsByName(String name) {

        return songRepository.findByNameContainingIgnoreCase(name);
    }
}
