package com.kcb.KCB_Board.domain.mapper;

import com.kcb.KCB_Board.domain.entity.Board;
import com.kcb.KCB_Board.domain.entity.dto.BoardDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BoardMapper {
    Board toEntity(BoardDto.Post postDto);
    BoardDto.Response toBoardResponse(Board board);
    List<BoardDto.Response> toBoardResponses(List<Board> boards);
    BoardDto.ResponseDetail toBoardResponseDetail(Board board);
}
