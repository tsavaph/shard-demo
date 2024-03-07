package com.example.sharddemo.service;

import com.example.sharddemo.configuration.DBSourceEnum;
import com.example.sharddemo.configuration.TransactionProcessor;
import com.example.sharddemo.dto.PostDto;
import com.example.sharddemo.entity.PostEntity;
import com.example.sharddemo.repository.RepositorySelector;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {

    private final RepositorySelector repositorySelector;
    private final TransactionProcessor transactionProcessor;

    public void saveAll(DBSourceEnum dbSource, List<PostDto> posts) {
        var postEntities = posts.stream()
                .map(this::map)
                .collect(Collectors.toList());

        var repo = repositorySelector.selectPostRepository(dbSource);

        transactionProcessor.process(
                () -> repo.saveAll(postEntities),
                dbSource
        );
    }

    private PostEntity map(PostDto postDto) {
        var entity = new PostEntity();
        entity.setName(postDto.getName());
        entity.setType(postDto.getType());
        return entity;
    }

}
