package com.kcb.KCB_Board.domain.entity.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class BoardDto {
    public static class Post {
        @NotBlank
        private String title;
        @NotNull
        private String content;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Response {
        private String title;
        private String createdAt;
    }

    public static class ResponseDetail {
        private String title;
        private String content;
        private String createdAt;
        private List<Response> relatedBoards = new ArrayList<>();
    }
}
