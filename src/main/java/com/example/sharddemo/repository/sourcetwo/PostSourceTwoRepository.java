package com.example.sharddemo.repository.sourcetwo;


import com.example.sharddemo.entity.sourcetwo.PostSourceTwo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostSourceTwoRepository extends JpaRepository<PostSourceTwo, Long> {

}