package org.example.socialnetworkingsite.service.photo;

import org.example.socialnetworkingsite.entites.Photo;

import java.awt.print.Pageable;
import java.util.List;

public interface PhotoService {
    Photo save(Photo photo);

    List<Photo> findAll();

    List<Photo> findAllByUserId(Long userId);

    List<Photo> getAllFromPageToPage( int page, int size);
}
