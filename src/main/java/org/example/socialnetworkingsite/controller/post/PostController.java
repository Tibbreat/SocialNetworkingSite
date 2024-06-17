package org.example.socialnetworkingsite.controller.post;

import org.example.socialnetworkingsite.constant.HTTPCode;
import org.example.socialnetworkingsite.dto.PostDTO;
import org.example.socialnetworkingsite.entites.Post;
import org.example.socialnetworkingsite.entites.User;
import org.example.socialnetworkingsite.jwt.JwtTokenProvider;
import org.example.socialnetworkingsite.model.ResponseModel;
import org.example.socialnetworkingsite.service.post.PostService;
import org.example.socialnetworkingsite.service.post.PostServiceImpl;
import org.example.socialnetworkingsite.service.user.UserService;
import org.example.socialnetworkingsite.service.user.UserServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    PostServiceImpl postService;


    @GetMapping("/getAll")
    public ResponseEntity<ResponseModel> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        List<PostDTO> postDTOS = new ArrayList<>();
        for (Post post : posts) {
            PostDTO postDTO = new PostDTO();
            BeanUtils.copyProperties(post, postDTO);
            postDTOS.add(postDTO);
        }
        if (posts.isEmpty()) {
            return ResponseEntity.status(HTTPCode.HTTP_NOT_FOUND)
                    .body(new ResponseModel(HTTPCode.HTTP_NOT_FOUND, "No posts found"));
        } else {
            return ResponseEntity.status(HTTPCode.HTTP_OK)
                    .body(new ResponseModel(HTTPCode.HTTP_OK, "Found " + posts.size() + " posts", postDTOS));
        }
    }

    @GetMapping("/getAllByPage")
    public ResponseEntity<ResponseModel> getAllPostsByPage(@RequestParam(required = false) int page,
                                                           @RequestParam(required = false) int size) {
        List<Post> posts = postService.getAllFromPageToPage(page, size);
        if (posts.isEmpty()) {
            return ResponseEntity.status(HTTPCode.HTTP_NOT_FOUND)
                    .body(new ResponseModel(HTTPCode.HTTP_NOT_FOUND, "No posts found"));
        } else {
            List<PostDTO> postDTOS = new ArrayList<>();
            for (Post post : posts) {
                PostDTO postDTO = new PostDTO();
                BeanUtils.copyProperties(post, postDTO);
                postDTOS.add(postDTO);
            }
            return ResponseEntity.status(HTTPCode.HTTP_OK)
                    .body(new ResponseModel(HTTPCode.HTTP_OK, "Found " + posts.size() + "posts", postDTOS));
        }
    }


    @PostMapping("/create")
    public ResponseEntity<ResponseModel> createPost(@RequestBody Post post) {

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
    public ResponseEntity<ResponseModel> deletePost(@RequestParam long postId) {

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
