package org.example.socialnetworkingsite.controller.comment;

import org.example.socialnetworkingsite.dto.CommentDTO;
import org.example.socialnetworkingsite.entites.Comment;
import org.example.socialnetworkingsite.entites.Post;
import org.example.socialnetworkingsite.entites.User;
import org.example.socialnetworkingsite.model.ResponseModel;
import org.example.socialnetworkingsite.service.comment.CommentServiceImpl;
import org.example.socialnetworkingsite.service.user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    CommentServiceImpl commentService;

    @Autowired
    UserServiceImpl userService;
    @Autowired
    private CommentServiceImpl commentServiceImpl;

    @PostMapping("/create")
    public ResponseEntity<ResponseModel> createComment(@RequestParam Long postId,
                                                       @RequestParam String description,
                                                       @RequestParam(required = false) Long commentID) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (description.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new ResponseModel(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Add new comment failed: Description is required"));
        }

        User user = User.builder().emailId(userDetails.getUsername()).build();
        Post post = Post.builder().id(postId).build();
        Comment parent = (commentID != null) ? Comment.builder().id(commentID).build() : null;

        Comment newComment = Comment.builder()
                .description(description)
                .user(user)
                .post(post)
                .comment(parent)
                .build();

        Comment createdComment = commentServiceImpl.addCommentIntoPost(postId, newComment);

        if (createdComment == null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new ResponseModel(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Add comment failed"));
        } else {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseModel(HttpStatus.CREATED.value(), "Add new comment successfully"));
        }
    }

    @GetMapping("/getCommentOfPost")
    public ResponseEntity<ResponseModel> getCommentOfPost(@RequestParam Long postId)  {
        List<Comment> comments = commentServiceImpl.getCommentsOfPost(postId);

        if (comments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseModel(HttpStatus.NOT_FOUND.value(), "No comment found"));
        }

        List<CommentDTO> commentDTOS = new ArrayList<>();
        for (Comment comment : comments) {

            commentDTOS.add(CommentDTO.builder()
                    .id(comment.getId())
                    .description(comment.getDescription())
                    .emailId(comment.getUser().getEmailId())
                    .build());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseModel(HttpStatus.OK.value(), "Found " + comments.size() + " comments", commentDTOS));
    }

    @GetMapping("/getCommentOfComment")
    public ResponseEntity<ResponseModel> getCommentOfComment(@RequestParam Long commentId) {
        List<Comment> comments = commentServiceImpl.getCommentOfComment(commentId);
        if (!comments.isEmpty()) {
            List<CommentDTO> commentDTOS = new ArrayList<>();
            for (Comment comment : comments) {

                commentDTOS.add(CommentDTO.builder()
                        .id(comment.getId())
                        .description(comment.getDescription())
                        .emailId(comment.getUser().getEmailId())
                        .build());
            }

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseModel(HttpStatus.OK.value(), "Found " + comments.size() + " comments of comment have id: " + commentId, commentDTOS));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseModel(HttpStatus.NOT_FOUND.value(), "No comment found"));
        }
    }
}

