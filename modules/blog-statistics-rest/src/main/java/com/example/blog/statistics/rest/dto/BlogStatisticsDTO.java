package com.example.blog.statistics.rest.dto;


import com.example.blogstatistics.model.BlogStatistics;

public record BlogStatisticsDTO(
        long blogStatisticsId,
        long companyId,
        long blogEntryId,
        long viewCount,
        String createDate,
        String modifiedDate
) {
    public static BlogStatisticsDTO from(BlogStatistics stat) {
        if (stat == null) {
            return null;
        }
        return new BlogStatisticsDTO(
                stat.getBlogStatisticsId(),
                stat.getCompanyId(),
                stat.getBlogEntryId(),
                stat.getViewCount(),
                stat.getCreateDate() != null ? stat.getCreateDate().toString() : null,
                stat.getModifiedDate() != null ? stat.getModifiedDate().toString() : null
        );
    }
}