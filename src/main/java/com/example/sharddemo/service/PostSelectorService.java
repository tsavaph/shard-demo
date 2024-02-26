package com.example.sharddemo.service;

import com.example.sharddemo.dto.PostDto;
import com.example.sharddemo.repository.PostRepository;
import com.example.sharddemo.repository.sourcedefault.PostSourceDefaultRepository;
import com.example.sharddemo.repository.sourceone.PostSourceOneRepository;
import com.example.sharddemo.repository.sourcetwo.PostSourceTwoRepository;
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
    private final PostSourceDefaultRepository defaultRepository;
    private final PostSourceOneRepository oneRepository;
    private final PostSourceTwoRepository twoRepository;

    public List<PostDto> saveAll(List<PostDto> postSourceOnes) {

        var notSavedPosts = new ArrayList<PostDto>();

        var groupedPosts = postSourceOnes
                .stream()
                .collect(Collectors.groupingBy(
                                p -> selectRepositoryByType(p.getType())
                        )
                );


        for (var entry : groupedPosts.entrySet()) {
            try {
                postService.saveAll(
                        entry.getKey(),
                        entry.getValue()
                );
            } catch (Exception exception) {
                log.error(exception.getMessage(), exception);
                notSavedPosts.addAll(postSourceOnes);
            }
        }

        return notSavedPosts;
    }

    private PostRepository selectRepositoryByType(Long type) {
        if (type > 0 && type <= 9) {
            return oneRepository;
        } else if (type > 10 && type <= 99) {
            return twoRepository;
        } else {
            return defaultRepository;
        }
    }

}
