package com.example.sharddemo.entity.sourceone;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "post")
public class PostSourceOne {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_post")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private Long type;

    public PostSourceOne(String name, Long type) {
        this.type = type;
        this.name = name;
    }

}