package com.example.sharddemo.controller;

import com.example.sharddemo.dto.PostDto;
import com.example.sharddemo.service.PostSelectorService;
import com.example.sharddemo.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@AllArgsConstructor
public class PostController {

    private final PostSelectorService postSelectorService;
    private final PostService postService;

    @GetMapping("/init-data")
    @ResponseBody
    public String initialData() {
        var posts = new ArrayList<PostDto>();
        posts.add(new PostDto("Post 1", 1L));
        posts.add(new PostDto("Post 2", 2L));
        posts.add(new PostDto("Post 3", 3L));

        posts.add(new PostDto("Post 11", 11L));
        posts.add(new PostDto("Post 12", 12L));
        posts.add(new PostDto("Post 13", 13L));

        posts.add(new PostDto("Post 101", 101L));
        posts.add(new PostDto("Post 102", 102L));
        posts.add(new PostDto("Post 103", 103L));

        postSelectorService.saveAll(posts);

        return "OK";
    }

}