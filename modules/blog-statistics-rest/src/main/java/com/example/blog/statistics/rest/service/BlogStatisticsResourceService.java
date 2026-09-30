package com.example.blog.statistics.rest.service;


import com.example.blog.statistics.rest.dto.BlogStatisticsDTO;
import com.example.blog.statistics.rest.dto.GetAllViewCountsQuery;
import com.example.blog.statistics.rest.dto.PagedResponseDTO;

public interface BlogStatisticsResourceService {

    PagedResponseDTO getPagedStats(GetAllViewCountsQuery query);

    BlogStatisticsDTO getViewCount(long blogEntryId, Long companyId);

    BlogStatisticsDTO incrementViewCount(long blogEntryId, Long companyId);

    void deleteViewCount(long blogEntryId, Long companyId) throws Exception;

    void deleteAll();
}
