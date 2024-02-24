package com.example.sharddemo.repository.sourceone;

import com.example.sharddemo.entity.sourceone.PostSourceOne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostSourceOneRepository extends JpaRepository<PostSourceOne, Long> {

}