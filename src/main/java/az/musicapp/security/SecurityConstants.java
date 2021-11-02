package az.musicapp.security;

import org.springframework.stereotype.Component;

@Component
public final class SecurityConstants {

    public static final String AUTHENTICATION_URLS = "/api/users/**";
    public static final String SONG_URLS = "/api/songs/**";
    public static final String ALBUM_URLS = "/api/albums/**";
    public static final String GENRE_URLS = "/api/genres/**";
    public static final String ARTIST_URLS = "/api/artists/**";
    public static final String COMMENT_URLS = "/api/comments/**";
    public static final String PLAYLIST_URLS = "/api/playlist/**";
    public static final String LIKE_URLS = "/api/likes/**";

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final long EXPIRATION_TIME = 360000;



    private SecurityConstants() {
    }


}
