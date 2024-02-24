package com.example.sharddemo.entity.sourcetwo;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "post")
public class PostSourceTwo {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_post")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private Long type;

    public PostSourceTwo(String name, Long type) {
        this.type = type;
        this.name = name;
    }

}