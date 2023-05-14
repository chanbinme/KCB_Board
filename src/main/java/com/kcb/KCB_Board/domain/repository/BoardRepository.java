package com.kcb.KCB_Board.domain.repository;

import com.kcb.KCB_Board.domain.entity.Board;
import com.kcb.KCB_Board.domain.entity.BoardDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<BoardDocument> searchByContent(String content);
}
