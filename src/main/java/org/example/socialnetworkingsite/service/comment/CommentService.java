package org.example.socialnetworkingsite.service.comment;

import org.example.socialnetworkingsite.entites.Comment;

import java.util.List;

public interface CommentService {
    Comment addCommentIntoPost(Long postId, Comment comment);

    List<Comment> getCommentsOfPost(Long postId);

    boolean updateComment(long commentId, String description);
}
