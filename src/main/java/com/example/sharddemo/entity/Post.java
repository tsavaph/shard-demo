package com.example.sharddemo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "post")
public class Post {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_post")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private Long type;


    public Post(String name, Long type) {
        this.name = name;
        this.type = type;
    }

}