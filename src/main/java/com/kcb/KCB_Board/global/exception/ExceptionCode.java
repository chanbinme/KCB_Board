package com.kcb.KCB_Board.global.exception;

import lombok.Getter;

public enum ExceptionCode {
    BOARD_NOT_FOUND(404, "게시물을 찾을 수 없습니다.");

    @Getter
    private final int status;
    @Getter
    private final String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
