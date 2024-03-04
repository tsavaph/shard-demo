package com.example.sharddemo.service;

import com.example.sharddemo.configuration.DBSourceEnum;
import com.example.sharddemo.configuration.TransactionRunner;
import com.example.sharddemo.dto.PostDto;
import com.example.sharddemo.entity.PostEntity;
import com.example.sharddemo.repository.sourcedefault.PostSourceDefaultRepository;
import com.example.sharddemo.repository.sourceone.PostSourceOneRepository;
import com.example.sharddemo.repository.sourcetwo.PostSourceTwoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {

    private final PostSourceDefaultRepository defaultRepository;
    private final PostSourceOneRepository oneRepository;
    private final PostSourceTwoRepository twoRepository;
    private final TransactionRunner transactionRunner;

    public void saveAll(DBSourceEnum postsByDbSource, List<PostDto> posts) {
        var postEntities = posts.stream()
                .map(this::map)
                .collect(Collectors.toList());

        if (postsByDbSource == DBSourceEnum.SOURCE_ONE) {
            transactionRunner.processOneTransaction(
                    () -> oneRepository.saveAll(postEntities)
            );
        } else if (postsByDbSource == DBSourceEnum.SOURCE_TWO) {
            transactionRunner.processTwoTransaction(
                    () -> twoRepository.saveAll(postEntities)
            );
        } else {
            transactionRunner.processDefaultTransaction(
                    () -> defaultRepository.saveAll(postEntities)
            );
        }
    }

    private PostEntity map(PostDto postDto) {
        var entity = new PostEntity();
        entity.setName(postDto.getName());
        entity.setType(postDto.getType());
        return entity;
    }

}
