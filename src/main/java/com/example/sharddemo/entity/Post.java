package com.example.sharddemo.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@MappedSuperclass
@Getter
@Setter
public class Post {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_post")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private Long type;

}