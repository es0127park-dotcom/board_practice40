package com.example.board_practice40.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class PageDTO<T> {

    private List<T> content; // 현재 페이지 게시글 목록
    private int page; // 현재 페이지 번호 (0부터 시작)
    private int totalPages; // 전체 페이지 수
    private long totalElements; // 전체 게시글 수
    private boolean first; // 첫 페이지 여부
    private boolean last; // 마지막 페이지 여부
    private int startPage; // 페이지 네비게이션 시작 번호
    private int endPage; // 페이지 네비게시션 끝 번호

    public PageDTO(List<T> content, int page, int size, long totalElements) {
        this.content = content;
        this.page = page;
        this.totalElements = totalElements;
        this.totalPages = (int) Math.ceil((double) totalElements / size);
        this.first = (page == 0);
        this.last = (page >= totalPages - 1);

        // 페이지 네비게이션 : 한 번에 5개씩 보여주기
        // 예 : 현재 7페이지 -> [5] [6] [7] [8] [9]
        int blockSize = 5;
        this.startPage = (page / blockSize) * blockSize;
        this.endPage = Math.min(startPage + blockSize - 1, totalPages - 1);
    }
}
