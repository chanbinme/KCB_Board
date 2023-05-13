package com.kcb.KCB_Board.global.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class MultiResponseDto<T> {
    private List<T> data;
}
