package com.example.board_practice40.dto;

import java.util.ArrayList;
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

    /**
     * Mustache용 헬퍼 메서드
     */

    // 이전 페이지 번호
    public int getPrevPage() {
        return page - 1;
    }

    // 다음 페이지 번호
    public int getNextPage() {
        return page + 1;
    }

    /**
     * 페이지 번호 목록 (Mustache에서 반복 출력용)
     * 예 : [ { index : 0 , number : 1 , active : true } , { index : 1 , number : 2 ,
     * active : false } , ...]
     */
    public List<PageNumber> getPageNumvers() {
        List<PageNumber> pages = new ArrayList<>();
        for (int i = startPage; i <= endPage; i++) {
            pages.add(new PageNumber(i, i + 1, i == page));
        }
        return pages;
    }

    @Getter
    public static class PageNumber {
        private int index; // 0-based (URL 파라미터용)
        private int number;
        private boolean active;

        public PageNumber(int index, int number, boolean active) {
            this.index = index;
            this.number = number;
            this.active = active;
        }
    }
}
