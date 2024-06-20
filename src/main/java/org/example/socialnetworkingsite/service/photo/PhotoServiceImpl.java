package org.example.socialnetworkingsite.service.photo;

import org.example.socialnetworkingsite.entites.Photo;
import org.example.socialnetworkingsite.repository.PhotoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PhotoServiceImpl implements PhotoService {
    @Autowired
    PhotoRepo photoRepo;

    @Override
    public Photo save(Photo photo) {
        return photoRepo.save(photo);
    }

    @Override
    public List<Photo> findAll() {
        return photoRepo.findAll();
    }

    @Override
    public List<Photo> findAllByUserId(Long userId) {
        return photoRepo.findAllById(Collections.singleton(userId));
    }

    @Override
    public List<Photo> getAllFromPageToPage(int page, int size) {
        Page<Photo> photos = photoRepo.findAll(PageRequest.of(page, size));
        return photos.getContent();
    }
}
