package org.example.socialnetworkingsite.repository;

import org.example.socialnetworkingsite.entites.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostId(Long postId);

    @Query("SELECT c FROM Comment c WHERE c.comment.id = :commentId")
    List<Comment> findAllByComment(@Param("commentId") Long commentId);
}
