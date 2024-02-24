package com.example.sharddemo.service;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class PostService {

    @Transactional
    public <T, ID> void saveAll(List<T> postSourceOnes, JpaRepository<T, ID> repository) {
        repository.saveAll(postSourceOnes);
    }

}
