package com.example.blog.statistics.rest.service.impl;

import com.example.blog.statistics.rest.dto.BlogStatisticsDTO;
import com.example.blog.statistics.rest.dto.GetAllViewCountsQuery;
import com.example.blog.statistics.rest.dto.PagedResponseDTO;
import com.example.blog.statistics.rest.service.BlogStatisticsResourceService;
import com.example.blogstatistics.dto.Filter;
import com.example.blogstatistics.dto.Page;
import com.example.blogstatistics.dto.PageRequest;
import com.example.blogstatistics.dto.SortAttribute;
import com.example.blogstatistics.dto.SortDirection;
import com.example.blogstatistics.model.BlogStatistics;

import com.example.blogstatistics.service.BlogStatisticsLocalService;

import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(service = BlogStatisticsResourceService.class)
public class BlogStatisticsResourceServiceImpl implements BlogStatisticsResourceService {

    @Reference
    private BlogStatisticsLocalService _blogStatisticsLocalService;

    @Override
    public PagedResponseDTO getPagedStats(GetAllViewCountsQuery query) {

        long resolvedCompanyId = resolveCompanyId(query.getCompanyId());
        Filter filter = new Filter(
                resolvedCompanyId, query.getBlogEntryId(), query.getMinViews(),
                query.getMaxViews());
        PageRequest pageRequest = new PageRequest(
                query.getPage(), query.getPageSize(), query.getSortAttribute(),
                query.getSortDirection());

        Page pageResult = _blogStatisticsLocalService.getPagedStats(
                filter, pageRequest);
        return PagedResponseDTO.from(pageResult);
    }

    @Override
    public BlogStatisticsDTO getViewCount(long blogEntryId, Long companyId) {
        long resolvedCompanyId = resolveCompanyId(companyId);
        BlogStatistics stat = _blogStatisticsLocalService.fetchByCompanyIdAndBlogEntryId(resolvedCompanyId, blogEntryId);

        return BlogStatisticsDTO.from(stat);
    }

    @Override
    public BlogStatisticsDTO incrementViewCount(long blogEntryId, Long companyId) {
        long resolvedCompanyId = resolveCompanyId(companyId);
        BlogStatistics stat = _blogStatisticsLocalService.incrementViewCount(resolvedCompanyId, blogEntryId);

        return BlogStatisticsDTO.from(stat);
    }

    @Override
    public void deleteViewCount(long blogEntryId, Long companyId) throws Exception {
        long resolvedCompanyId = resolveCompanyId(companyId);
        _blogStatisticsLocalService.deleteByCompanyIdAndBlogEntryId(resolvedCompanyId, blogEntryId);
    }

    @Override
    public void deleteAll() {
        _blogStatisticsLocalService.deleteAllStats();
    }

    private long resolveCompanyId(Long companyId) {
        return (companyId != null && companyId > 0) ? companyId : CompanyThreadLocal.getCompanyId();
    }
}
