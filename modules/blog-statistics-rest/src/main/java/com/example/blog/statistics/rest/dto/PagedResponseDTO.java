package com.example.blog.statistics.rest.dto;

import com.example.blogstatistics.dto.Page;
import com.example.blogstatistics.model.BlogStatistics;

import java.util.Collections;
import java.util.List;

public record PagedResponseDTO(
        List<BlogStatisticsDTO> items,
        int page,
        int pageSize,
        long totalCount,
        int totalPages
) {
    public static PagedResponseDTO from(Page pageResult) {
        if (pageResult == null) {
            return new PagedResponseDTO(Collections.emptyList(), 1, 0, 0, 0);
        }

        List<BlogStatistics> domainItems = (List<BlogStatistics>) pageResult.items();
        List<BlogStatisticsDTO> dtos = (domainItems != null)
                ? domainItems.stream().map(BlogStatisticsDTO::from).toList()
                : Collections.emptyList();

        return new PagedResponseDTO(
                dtos,
                pageResult.page(),
                pageResult.pageSize(),
                pageResult.totalCount(),
                pageResult.getTotalPages()
        );
    }
}