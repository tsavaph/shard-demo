package com.example.sharddemo.controller;

import com.example.sharddemo.entity.Post;
import com.example.sharddemo.configuration.DBContextHolder;
import com.example.sharddemo.configuration.DBTypeEnum;
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
                DBContextHolder.setCurrentDb(DBTypeEnum.CLIENT_B);
                break;
            case "client-a":
                DBContextHolder.setCurrentDb(DBTypeEnum.CLIENT_A);
                break;
        }
        return postService.findAll();
    }

    @GetMapping("/init-data")
    @ResponseBody
    public String initialData() {
        var posts = new ArrayList<Post>();
        posts.add(new Post("Post 1", 1L));
        posts.add(new Post("Post 2", 2L));
        posts.add(new Post("Post 3", 3L));

        posts.add(new Post("Post 11", 11L));
        posts.add(new Post("Post 12", 12L));
        posts.add(new Post("Post 13", 13L));

        posts.add(new Post("Post 101", 101L));
        posts.add(new Post("Post 102", 102L));
        posts.add(new Post("Post 103", 103L));

        var notSavedPosts = postSelectorService.saveAll(posts);

        return notSavedPosts.toString();
    }

}