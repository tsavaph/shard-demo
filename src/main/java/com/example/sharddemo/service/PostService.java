package com.example.sharddemo.service;

import com.example.sharddemo.entity.Post;
import com.example.sharddemo.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public void save(Post p) {
        postRepository.save(p);
    }

    @Transactional
    public void saveAll(List<Post> posts) {
        postRepository.saveAll(posts);
    }

    @Transactional(readOnly = true)
    public List<Post> findAll() {
        return postRepository.findAll();
    }

}
