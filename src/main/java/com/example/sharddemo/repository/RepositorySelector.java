package com.example.sharddemo.repository;

import com.example.sharddemo.configuration.DBSourceEnum;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class RepositorySelector {
    private final List<PostRepository> repositories;

    public PostRepository selectPostRepository(DBSourceEnum dbSource) {
        return repositories.get(dbSource.ordinal());
    }

}
