package com.example.blogstatistics.dto;

import java.util.List;

public record Page(
        List<?> items,
        int page,
        int pageSize,
        long totalCount
) {
    public int getTotalPages() {
        if (pageSize <= 0) return 0;
        return (int) Math.ceil((double) totalCount / pageSize);
    }
}