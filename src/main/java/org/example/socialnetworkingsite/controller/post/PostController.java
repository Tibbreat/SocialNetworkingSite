package org.example.socialnetworkingsite.controller.post;

import jakarta.servlet.http.HttpServletRequest;
import org.example.socialnetworkingsite.constant.HTTPCode;
import org.example.socialnetworkingsite.entites.Post;
import org.example.socialnetworkingsite.entites.User;
import org.example.socialnetworkingsite.jwt.JwtTokenProvider;
import org.example.socialnetworkingsite.model.ResponseModel;
import org.example.socialnetworkingsite.service.post.PostService;
import org.example.socialnetworkingsite.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {
    @Autowired
    UserService userService;
    @Autowired
    PostService postService;
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @GetMapping("/getAll")
    public ResponseEntity<ResponseModel> getAllPosts(HttpServletRequest request) {
        List<Post> posts = postService.getAllPosts();
        if (posts.isEmpty()) {
            return ResponseEntity.status(HTTPCode.HTTP_NOT_FOUND)
                    .body(new ResponseModel(HTTPCode.HTTP_NOT_FOUND, "No posts found"));
        } else {
            return ResponseEntity.status(HTTPCode.HTTP_OK)
                    .body(new ResponseModel(HTTPCode.HTTP_OK, "Found " + posts.size() + " posts", posts));
        }

    }

    @GetMapping("/getAllByPage")
    public ResponseEntity<ResponseModel> getAllPostsByPage(@RequestParam(required = false) int page,
                                                           @RequestParam(required = false) int size,
                                                           HttpServletRequest request) {

        List<Post> posts = postService.getAllFromPageToPage(page, size);
        if (posts.isEmpty()) {
            return ResponseEntity.status(HTTPCode.HTTP_NOT_FOUND)
                    .body(new ResponseModel(HTTPCode.HTTP_NOT_FOUND, "No posts found"));
        } else {
            return ResponseEntity.status(HTTPCode.HTTP_OK)
                    .body(new ResponseModel(HTTPCode.HTTP_OK, "Found " + posts.size() + "posts", posts));
        }
    }


    @PostMapping("/create")
    public ResponseEntity<ResponseModel> createPost(@RequestBody Post post, HttpServletRequest request) {


        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (post.getDescription() == null || post.getDescription().isEmpty() ||
                post.getImgUrl() == null || post.getImgUrl().isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new ResponseModel(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Create new post failed: Description and Img URL are required"));
        }
        // Create a new User object and set the email ID
        User user = new User();
        user.setEmailId(userDetails.getUsername());
        post.setUser(user);

        // Create the post using the post service
        Post createdPost = postService.createPost(post);
        if (createdPost != null) {
            return ResponseEntity.status(HTTPCode.HTTP_CREATED)
                    .body(new ResponseModel(HTTPCode.HTTP_CREATED, "Create new post successfully"));
        } else {
            return ResponseEntity.status(HTTPCode.HTTP_UNPROCESSABLE_ENTITY)
                    .body(new ResponseModel(HTTPCode.HTTP_UNPROCESSABLE_ENTITY, "Create new post failed"));
        }

    }

    @PostMapping("/delete")
    public ResponseEntity<ResponseModel> deletePost(@RequestParam long postId, HttpServletRequest request) {

        boolean post = postService.deletePost(postId);
        if (post) {
            return ResponseEntity.status(HTTPCode.HTTP_OK)
                    .body(new ResponseModel(HTTPCode.HTTP_OK, "Delete post successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new ResponseModel(HTTPCode.HTTP_UNPROCESSABLE_ENTITY, "Delete post failed"));
        }
    }
}
