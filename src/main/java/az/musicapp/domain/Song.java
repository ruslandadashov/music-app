package az.musicapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String link;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Artist artist;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnore
    private Album album;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "SONG_GENRE",
            joinColumns = @JoinColumn(name = "SONG_ID"),
            inverseJoinColumns = @JoinColumn(name = "GENRE_ID"))
    @ToString.Exclude
    @JsonIgnore
    private List<Genre> genres = new ArrayList<>();

    @ManyToMany(mappedBy = "likedSongs", fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnore
    private List<User> likedUsers = new ArrayList<>();

    @ManyToMany(mappedBy = "songs", fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnore
    private List<Playlist> playlists = new ArrayList<>();

    @OneToMany(mappedBy = "song", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();

    public void addGenre(Genre genre) {
        this.genres.add(genre);
    }

    public void removeGenre(Genre genre) {
        this.genres.remove(genre);
    }

    public void addUser(User user) {
        this.likedUsers.add(user);
    }

    public void removeUser(User user) {
        this.likedUsers.remove(user);
    }

    public void addPlaylist(Playlist playlist) {
        this.playlists.add(playlist);
    }

    public void removePlaylist(Playlist playlist) {
        this.playlists.remove(playlist);
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public void removeComment(Comment comment) {
        this.comments.remove(comment);
    }
}
