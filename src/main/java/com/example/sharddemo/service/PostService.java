package com.example.sharddemo.service;

import com.example.sharddemo.configuration.DBSourceEnum;
import com.example.sharddemo.entity.Post;
import com.example.sharddemo.repository.PostRepository;
import com.example.sharddemo.repository.sourcedefault.PostSourceDefaultRepository;
import com.example.sharddemo.repository.sourceone.PostSourceOneRepository;
import com.example.sharddemo.repository.sourcetwo.PostSourceTwoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class PostService {

    private final PostSourceDefaultRepository defaultRepository;
    private final PostSourceOneRepository oneRepository;
    private final PostSourceTwoRepository twoRepository;

    @Transactional
    public void saveAll(List<Post> posts, DBSourceEnum dbSource) {
        var repo = selectRepositoryByDataSource(dbSource);
        repo.saveAll(posts);
    }

    private PostRepository selectRepositoryByDataSource(DBSourceEnum dbSource) {
        if (dbSource == DBSourceEnum.SOURCE_ONE) {
            return oneRepository;
        } else if (dbSource == DBSourceEnum.SOURCE_TWO) {
            return twoRepository;
        } else {
            return defaultRepository;
        }
    }

}
