package az.musicapp.services;

import az.musicapp.domain.Album;

public interface AlbumService {

     Album findAlbumById(Long id) ;

     Iterable<Album> findAllAlbums() ;

     Iterable<Album> findAlbumsByName(String name) ;
}
