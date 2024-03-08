package com.example.sharddemo.repository.sourcetwo;


import com.example.sharddemo.repository.PostRepository;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Repository;

@Repository
@Order(2)
public interface PostSourceTwoRepository extends PostRepository {

}