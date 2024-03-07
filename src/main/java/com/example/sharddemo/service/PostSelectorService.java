package com.example.sharddemo.service;

import com.example.sharddemo.configuration.DBContextHolder;
import com.example.sharddemo.configuration.DBSourceEnum;
import com.example.sharddemo.dto.PostDto;
import com.example.sharddemo.entity.Post;
import com.example.sharddemo.entity.PostDefault;
import com.example.sharddemo.entity.PostOne;
import com.example.sharddemo.entity.PostTwo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostSelectorService {

    private final PostService postService;

    public List<PostDto> saveAll(List<PostDto> posts) {

        var notSavedPosts = new ArrayList<PostDto>();

        var groupedPosts = posts
                .stream()
                .collect(Collectors.groupingBy(
                                p -> selectDataSourceByType(p.getType())
                        )
                );


        for (var entry : groupedPosts.entrySet()) {
            var dbSource = entry.getKey();
            var postDtos = entry.getValue();
            DBContextHolder.setCurrentDb(dbSource);
            try {
                postService.saveAll(
                        postDtos.stream()
                                .map(p -> map(p, dbSource))
                                .collect(Collectors.toList())
                );
            } catch (Exception exception) {
                notSavedPosts.addAll(postDtos);
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

    private Post map(PostDto postDto, DBSourceEnum dbSource) {
        Post p;
        if (dbSource == DBSourceEnum.SOURCE_ONE) {
            p = new PostOne();
        } else if (dbSource == DBSourceEnum.SOURCE_TWO) {
            p = new PostTwo();
        } else {
            p = new PostDefault();
        }
        p.setType(postDto.getType());
        p.setName(postDto.getName());
        return p;
    }

}
