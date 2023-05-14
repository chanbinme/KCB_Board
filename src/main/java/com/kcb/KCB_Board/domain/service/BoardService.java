package com.kcb.KCB_Board.domain.service;

import com.kcb.KCB_Board.domain.entity.Board;
import com.kcb.KCB_Board.domain.entity.BoardDocument;
import com.kcb.KCB_Board.domain.entity.dto.BoardDto;
import com.kcb.KCB_Board.domain.entity.dto.BoardDto.ResponseDetail;
import com.kcb.KCB_Board.domain.mapper.BoardMapper;
import com.kcb.KCB_Board.domain.repository.BoardRepository;
import com.kcb.KCB_Board.domain.repository.BoardSearchRepository;
import com.kcb.KCB_Board.global.exception.BusinessLogicException;
import com.kcb.KCB_Board.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardSearchRepository boardSearchRepository;
    private final BoardMapper boardMapper;

    @Transactional
    public ResponseDetail createBoard(BoardDto.Post postDto) {
        Board board = boardMapper.toEntity(postDto);
        Board saveBoard = boardRepository.save(board);
        BoardDocument boardDocument = BoardDocument.from(saveBoard);

        // 게시글의 content를 단어별로 분리
        List<String> words = splitContent(boardDocument.getContent());

        // 게시글에서 자주 쓰이는 단어는 excludedWords에 저장
        List<String> excludedWords = getExcludedWords(words);
        words.removeAll(excludedWords);

        // 게시글에서 각 단어의 빈도수를 계산하여 저장
        Map<String, Long> documentWordFrequencies = countWords(words);
        boardDocument.setDocumentWordFrequencies(documentWordFrequencies);
        saveBoardDocument(boardDocument);

        return boardMapper.toBoardResponseDetail(saveBoard);
    }

    private void saveBoardDocument(BoardDocument boardDocument) {
        boardSearchRepository.save(boardDocument);
    }

    public ResponseDetail findById(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> {
                    log.debug("BoardService.findById exception occur boardId : {}", boardId);
                    throw new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND);
                });

        return boardMapper.toBoardResponseDetail(board);
    }

    public List<BoardDto.Response> findAll() {
        List<Board> boards = boardRepository.findAll();

        return boardMapper.toBoardResponses(boards);
    }

    private List<String> getExcludedWords(List<String> words) {
        Map<String, Long> wordFrequencies = countWords(words);
        int totalCount = words.size();
        List<String> excludeWords = new ArrayList<>();

        for (Map.Entry<String, Long> entry : wordFrequencies.entrySet()) {
            if (entry.getValue() > 0.6 * totalCount) {
                excludeWords.add(entry.getKey());
            }
        }

        return excludeWords;
    }

    private Map<String, Long> countWords(List<String> words) {
        return words.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    private List<String> splitContent(String content) {
        return Arrays.stream(content.split("\\s+"))
                .map(word -> word.replaceAll("[^a-zA-Z0-9]", ""))
                .filter(word -> !word.isEmpty())
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }
}
