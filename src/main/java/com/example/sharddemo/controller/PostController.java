package com.example.sharddemo.controller;

import com.example.sharddemo.configuration.DBContextHolder;
import com.example.sharddemo.configuration.DBSourceEnum;
import com.example.sharddemo.dto.PostDto;
import com.example.sharddemo.entity.Post;
import com.example.sharddemo.service.PostSelectorService;
import com.example.sharddemo.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@AllArgsConstructor
public class PostController {

    private final PostSelectorService postSelectorService;
    private final PostService postService;

    // test -> main DB
    // test?client=client-a -> Client A DB
    // test?client=client-b -> Client B DB
    @GetMapping("/test")
    @ResponseBody
    public Iterable<Post> getTest(@RequestParam(defaultValue = "main") String client) {
        switch (client) {
            case "client-b":
                DBContextHolder.setCurrentDb(DBSourceEnum.SOURCE_TWO);
                break;
            case "client-a":
                DBContextHolder.setCurrentDb(DBSourceEnum.SOURCE_ONE);
                break;
        }
        return postService.findAll();
    }

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

        var notSavedPosts = postSelectorService.saveAll(posts);

        return notSavedPosts.toString();
    }

}