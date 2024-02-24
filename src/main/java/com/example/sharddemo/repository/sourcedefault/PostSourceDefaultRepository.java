package com.example.sharddemo.repository.sourcedefault;

import com.example.sharddemo.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostSourceDefaultRepository extends JpaRepository<Post, Long> {

}