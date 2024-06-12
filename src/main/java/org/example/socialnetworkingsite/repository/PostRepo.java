package org.example.socialnetworkingsite.repository;

import org.example.socialnetworkingsite.entites.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {
    Post findById(long id);
}
