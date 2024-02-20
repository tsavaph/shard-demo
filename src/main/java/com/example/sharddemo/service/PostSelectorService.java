package com.example.sharddemo.service;

import com.example.sharddemo.configuration.DBContextHolder;
import com.example.sharddemo.configuration.DBSourceEnum;
import com.example.sharddemo.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostSelectorService {

    private final PostService postService;

    public List<Post> saveAll(List<Post> posts) {

        var notSavedPosts = new ArrayList<Post>();

        var groupedPosts = posts
                .stream()
                .collect(Collectors.groupingBy(
                                p -> selectDataSourceByType(p.getType())
                        )
                );


        for (var entry : groupedPosts.entrySet()) {
            DBContextHolder.setCurrentDb(entry.getKey());
            try {
                postService.saveAll(entry.getValue());
            } catch (Exception exception) {
                notSavedPosts.addAll(posts);
            }
        }

        return notSavedPosts;
    }

    private DBSourceEnum selectDataSourceByType(Long type) {
        if (type > 0 && type <= 9) {
            return DBSourceEnum.SOURCE_ONE;
        } else if (type > 10 && type <= 99) {
            return DBSourceEnum.SOURCE_TWO;
        } else {
            return DBSourceEnum.DEFAULT;
        }
    }

}
