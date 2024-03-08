package com.example.sharddemo.repository.sourceone;

import com.example.sharddemo.repository.PostRepository;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Repository;

@Repository
@Order(1)
public interface PostSourceOneRepository extends PostRepository {

}