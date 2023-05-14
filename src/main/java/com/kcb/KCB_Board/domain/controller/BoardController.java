package com.kcb.KCB_Board.domain.controller;

import com.kcb.KCB_Board.domain.entity.dto.BoardDto;
import com.kcb.KCB_Board.domain.entity.dto.BoardDto.ResponseDetail;
import com.kcb.KCB_Board.domain.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {
    private final BoardService boardService;

    @PostMapping()
    public ResponseEntity<ResponseDetail> createBoard(@Valid @RequestBody BoardDto.Post postDto) {
        ResponseDetail response = boardService.createBoard(postDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{board-id}")
    public ResponseEntity<ResponseDetail> findBoard(@PathVariable("board-id") Long boardId) {
        ResponseDetail response = boardService.findById(boardId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<BoardDto.Response>> findAll() {
        List<BoardDto.Response> responseList = boardService.findAll();

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }
}
