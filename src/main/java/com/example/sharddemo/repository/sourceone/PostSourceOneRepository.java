package com.example.sharddemo.repository.sourceone;

import com.example.sharddemo.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostSourceOneRepository extends JpaRepository<Post, Long> {

}