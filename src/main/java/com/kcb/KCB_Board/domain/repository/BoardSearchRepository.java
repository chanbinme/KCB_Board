package com.kcb.KCB_Board.domain.repository;

import com.kcb.KCB_Board.domain.entity.BoardDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface BoardSearchRepository extends ElasticsearchRepository<BoardDocument, Long> {
    List<BoardDocument> findByContent(String content, Pageable pageable);
}
