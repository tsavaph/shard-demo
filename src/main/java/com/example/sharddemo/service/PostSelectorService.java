package com.example.sharddemo.service;

import com.example.sharddemo.configuration.DBSourceEnum;
import com.example.sharddemo.dto.PostDto;
import com.example.sharddemo.entity.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostSelectorService {

    private final PostService postService;

    public List<PostDto> saveAll(List<PostDto> postSourceOnes) {

        var notSavedPosts = new ArrayList<PostDto>();

        var groupedPosts = postSourceOnes
                .stream()
                .collect(Collectors.groupingBy(
                                p -> selectDataSourceByType(p.getType())
                        )
                );


        for (var entry : groupedPosts.entrySet()) {
            try {
                postService.saveAll(
                        entry.getValue()
                                .stream()
                                .map(this::map)
                                .collect(Collectors.toList()),
                        entry.getKey()
                );
            } catch (Exception exception) {
                log.error(exception.getMessage(), exception);
                notSavedPosts.addAll(postSourceOnes);
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

    private Post map(PostDto postDto) {
        var entity = new Post();
        entity.setName(postDto.getName());
        entity.setType(postDto.getType());
        return entity;
    }

}
