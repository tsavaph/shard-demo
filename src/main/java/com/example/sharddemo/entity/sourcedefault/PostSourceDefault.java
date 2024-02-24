package com.example.sharddemo.entity.sourcedefault;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "post")
public class PostSourceDefault {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_post")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private Long type;

    public PostSourceDefault(String name, Long type) {
        this.type = type;
        this.name = name;
    }

}