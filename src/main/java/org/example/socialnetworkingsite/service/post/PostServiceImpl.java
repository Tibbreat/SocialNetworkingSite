package org.example.socialnetworkingsite.service.post;

import org.example.socialnetworkingsite.entites.Post;
import org.example.socialnetworkingsite.repository.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepo postRepo;

    @Override
    public Post createPost(Post post) {
        return postRepo.save(post);
    }

    @Override
    public Post updatePost(Post post) {
        return postRepo.save(post);
    }

    @Override
    public boolean deletePost(long postId) {
        try {
            postRepo.delete(getPostById(postId));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepo.findAll();
    }

    @Override
    public Post getPostById(long id) {
        return postRepo.findById(id).orElse(null);
    }

    @Override
    public List<Post> getAllFromPageToPage(int page, int pageSize) {
        Page<Post> pagePost = postRepo.findAll(PageRequest.of(page, pageSize));
        return pagePost.getContent();
    }
}
