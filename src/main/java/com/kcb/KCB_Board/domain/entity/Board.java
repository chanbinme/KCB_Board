package com.kcb.KCB_Board.domain.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.beans.ConstructorProperties;
import java.time.LocalDateTime;

@Entity
@Getter
@Document(indexName = "boards")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {
    @CreatedDate
    private final LocalDateTime createdAt = LocalDateTime.now();
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;

    @Builder
    @ConstructorProperties({"title", "content"})
    public Board(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
