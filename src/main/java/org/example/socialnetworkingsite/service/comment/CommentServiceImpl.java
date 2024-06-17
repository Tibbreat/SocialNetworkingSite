package org.example.socialnetworkingsite.service.comment;

import org.example.socialnetworkingsite.entites.Comment;
import org.example.socialnetworkingsite.repository.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepo commentRepo;

    @Override
    public Comment addCommentIntoPost(Long postId, Comment comment) {
        return commentRepo.save(comment);
    }

    @Override
    public List<Comment> getCommentsOfPost(Long postId) {
        return commentRepo.findAllByPostId(postId);
    }

    @Override
    public boolean updateComment(long commentId, String description) {
        Optional<Comment> commentExist = commentRepo.findById(commentId);
        if (commentExist.isPresent()) {
            Comment comment = commentExist.get();
            comment.setDescription(description);
            commentRepo.save(comment);
            return true;
        } else {
            return false;
        }
    }
    public List<Comment> getCommentOfComment(Long commentId) {
        return commentRepo.findAllByComment(commentId);
    }
}
