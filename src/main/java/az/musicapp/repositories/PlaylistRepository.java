package az.musicapp.repositories;

import az.musicapp.domain.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    @Modifying
    @Transactional
    @Query(value = "delete from Playlist p where p.id = ?1")
    void deleteById(Long id);

    @Modifying
    @Transactional
    @Query(value = "delete from song_playlist where song_id = ?1",nativeQuery = true)
    void deleteSongById(Long id);

}
