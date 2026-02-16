package com.example.board_practice40.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.board_practice40.entiry.Board;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class BoardRepository {

    private final EntityManager em;

    // 저장
    public Board save(Board board) {
        em.persist(board);
        return board;
    }

    // 단건 조회
    public Board findById(Long id) {
        return em.find(Board.class, id);
    }

    // 전체 목록 (페이징)
    public List<Board> findAll(int page, int size) {
        return em.createQuery("SELECT b FROM Board b ORDER BY b.id DESC", Board.class)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
    }

    // 전체 게시글 수
    public long count() {
        return em.createQuery("SELECT COUNT(b) FROM Board b", Long.class)
                .getSingleResult();
    }

    // 검색 + 페이징
    public List<Board> findBySearch(String searchType, String keyword, int page, int size) {
        String jpql = createSearchJpql(searchType);
        return em.createQuery(jpql, Board.class)
                .setParameter("keyword", "%" + keyword + "%")
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
    }

    // 검색 결과 수
    public long countBySearch(String searchType, String keyword) {
        String jpql = createCountJpql(searchType);
        return em.createQuery(jpql, Long.class)
                .setParameter("keyword", "%" + keyword + "%")
                .getSingleResult();
    }

    // private 헬퍼 메서드

    private String createSearchJpql(String searchType) {
        String where = createWhereClause(searchType);
        return "SELECT b FROM Board b WHERE " + where + " ORDER BY b.id DESC";
    }

    private String createCountJpql(String searchType) {
        String where = createWhereClause(searchType);
        return "SELECT COUNT(b) FROM Board b WHERE " + where;
    }

    private String createWhereClause(String searchType) {
        return switch (searchType) {
            case "title" -> "LOWER(b.title) LIKE LOWER(:keyword)";
            case "writer" -> "LOWER(b.writer) LIKE LOWER(:keyword)";
            case "titleContent" -> "LOWER(b.title) LIKE LOWER(:keyword) OR LOWER(b.content) LIKE LOWER(:keyword)";
            default -> "LOWER(b.title) LIKE LOWER(:keyword)";
        };
    }
}
