package com.jochman.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Keyword {
    @Id
    @GeneratedValue
    private Long keywordId;
    private String word;
}
