package az.musicapp.repositories;

import az.musicapp.domain.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

    Iterable<Song> findByNameContainingIgnoreCase(String name);
}
