package com.example.sharddemo.entity;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Builder
@Table(name = "post")
public class PostTwo extends Post {

}