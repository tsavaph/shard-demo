package com.example.sharddemo.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@EqualsAndHashCode
@Setter
@Getter
public class PostDto {

    private String name;
    private Long type;

}
