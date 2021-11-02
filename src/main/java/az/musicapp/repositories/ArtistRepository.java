package az.musicapp.repositories;

import az.musicapp.domain.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {

    Iterable<Artist> findByNameContainingIgnoreCase(String name);
}
