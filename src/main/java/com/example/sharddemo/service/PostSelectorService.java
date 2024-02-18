package com.example.sharddemo.service;

import com.example.sharddemo.configuration.DBContextHolder;
import com.example.sharddemo.configuration.DBTypeEnum;
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

        var groupedPosts = posts.stream()
                .collect(Collectors.groupingBy(Post::getType));

        for (var entry : groupedPosts.entrySet()) {
            selectDataSourceByType(entry.getKey());
            try {
                postService.saveAll(entry.getValue());
            } catch (Exception exception) {
                notSavedPosts.addAll(posts);
            }
        }

        return notSavedPosts;
    }

    private void selectDataSourceByType(Long type) {
        if (type > 0 && type <= 9) {
            DBContextHolder.setCurrentDb(DBTypeEnum.MAIN);
        } else if (type > 10 && type <= 99) {
            DBContextHolder.setCurrentDb(DBTypeEnum.CLIENT_A);
        } else {
            DBContextHolder.setCurrentDb(DBTypeEnum.CLIENT_B);
        }
    }

}
