package com.example.sharddemo.repository;

import com.example.sharddemo.configuration.DBSourceEnum;
import com.example.sharddemo.repository.sourcedefault.PostSourceDefaultRepository;
import com.example.sharddemo.repository.sourceone.PostSourceOneRepository;
import com.example.sharddemo.repository.sourcetwo.PostSourceTwoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RepositorySelector {

    private final PostSourceDefaultRepository defaultRepository;
    private final PostSourceOneRepository oneRepository;
    private final PostSourceTwoRepository twoRepository;


    public PostRepository selectPostRepository(DBSourceEnum dbSource) {
        if (dbSource == DBSourceEnum.SOURCE_ONE) {
            return oneRepository;
        } else if (dbSource == DBSourceEnum.SOURCE_TWO) {
            return twoRepository;
        } else {
            return defaultRepository;
        }
    }

}
