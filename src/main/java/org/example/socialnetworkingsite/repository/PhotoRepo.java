package org.example.socialnetworkingsite.repository;

import org.example.socialnetworkingsite.entites.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepo extends JpaRepository<Photo, Long> {
}
