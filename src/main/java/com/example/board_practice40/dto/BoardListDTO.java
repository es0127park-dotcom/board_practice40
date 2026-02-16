package com.example.board_practice40.dto;

import java.time.LocalDateTime;

import com.example.board_practice40.entiry.Board;

import lombok.Getter;

@Getter
public class BoardListDTO {

    private Long id;
    private String title;
    private String writer;
    private int viewCount;
    private LocalDateTime createdAt;

    public BoardListDTO(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.writer = board.getWriter();
        this.viewCount = board.getViewCount();
        this.createdAt = board.getCreatedAt();
    }
}
