package org.example.socialnetworkingsite.controller.post;

import org.example.socialnetworkingsite.constant.HTTPCode;
import org.example.socialnetworkingsite.entites.Post;
import org.example.socialnetworkingsite.entites.User;
import org.example.socialnetworkingsite.jwt.JwtTokenProvider;
import org.example.socialnetworkingsite.model.ResponseModel;
import org.example.socialnetworkingsite.service.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @PostMapping("/create")
    public ResponseEntity<ResponseModel> createPost(@RequestBody Post post) {
        // Validate the post description and image URL
        if (post.getDescription() == null || post.getDescription().isEmpty() ||
                post.getImgUrl() == null || post.getImgUrl().isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new ResponseModel(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Create new post failed: Description and Img URL are required"));
        }

        // Get user details from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails userDetails)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // Create a new User object and set the email ID
        User user = new User();
        user.setEmailId(userDetails.getUsername());
        post.setUser(user);

        // Create the post using the post service
        Post createdPost = postService.createPost(post);
        if (createdPost != null) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseModel(HttpStatus.CREATED.value(), "Create new post successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new ResponseModel(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Create new post failed"));
        }
    }

}
