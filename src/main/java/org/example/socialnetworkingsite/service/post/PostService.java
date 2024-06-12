package org.example.socialnetworkingsite.service.post;

import org.example.socialnetworkingsite.entites.Post;

import java.util.List;

public interface PostService {
    Post createPost(Post post);
    Post updatePost(Post post);
    Post deletePost(Post post);
    List<Post> getAllPosts();
    Post getPostById(long id);

}
