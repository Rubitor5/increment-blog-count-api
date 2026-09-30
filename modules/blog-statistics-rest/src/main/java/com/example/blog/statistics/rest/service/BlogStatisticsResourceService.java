package com.example.blog.statistics.rest.service;


import com.example.blog.statistics.rest.dto.BlogStatisticsDTO;
import com.example.blog.statistics.rest.dto.PagedResponseDTO;

public interface BlogStatisticsResourceService {

    PagedResponseDTO getPagedStats(Long companyId, Long blogEntryId, Long minViews, Long maxViews, int page, int pageSize);

    BlogStatisticsDTO getViewCount(long blogEntryId, Long companyId);

    BlogStatisticsDTO incrementViewCount(long blogEntryId, Long companyId);

    void deleteViewCount(long blogEntryId, Long companyId) throws Exception;

    void deleteAll();
}
