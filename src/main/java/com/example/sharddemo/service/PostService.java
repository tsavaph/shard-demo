package com.example.sharddemo.service;

import com.example.sharddemo.dto.PostDto;
import com.example.sharddemo.entity.PostEntity;
import com.example.sharddemo.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {

    @Transactional
    public void saveAll(PostRepository postRepository, List<PostDto> posts) {
        postRepository.saveAllAndFlush(
                posts.stream()
                        .map(this::map)
                        .collect(Collectors.toList())
        );
    }

    private PostEntity map(PostDto postDto) {
        var entity = new PostEntity();
        entity.setName(postDto.getName());
        entity.setType(postDto.getType());
        return entity;
    }

}
