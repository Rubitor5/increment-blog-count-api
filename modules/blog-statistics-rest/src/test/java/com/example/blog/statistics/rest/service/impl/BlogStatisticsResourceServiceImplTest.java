package com.example.blog.statistics.rest.service.impl;

import com.example.blog.statistics.rest.dto.BlogStatisticsDTO;
import com.example.blog.statistics.rest.dto.GetAllViewCountsQuery;
import com.example.blog.statistics.rest.dto.PagedResponseDTO;
import com.example.blogstatistics.dto.Filter;
import com.example.blogstatistics.dto.Page;
import com.example.blogstatistics.dto.PageRequest;
import com.example.blogstatistics.dto.SortAttribute;
import com.example.blogstatistics.dto.SortDirection;
import com.example.blogstatistics.exception.InvalidSortException;
import com.example.blogstatistics.model.BlogStatistics;
import com.example.blogstatistics.service.BlogStatisticsLocalService;

import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BlogStatisticsResourceServiceImplTest {

    private static final long DEFAULT_COMPANY_ID = 59206091757698L;
    private static final long EXPLICIT_COMPANY_ID = 99999999999999L;
    private static final long BLOG_ENTRY_ID = 1001L;

    @Mock
    private BlogStatisticsLocalService blogStatisticsLocalService;

    @InjectMocks
    private BlogStatisticsResourceServiceImpl resourceService;

    // =========================================================================
    // getPagedStats Tests
    // =========================================================================

    @Test
    public void getAllViewCountsQuery_WhenPaginationIsUnset_UsesDefaults() {
        GetAllViewCountsQuery query = new GetAllViewCountsQuery();

        Assert.assertEquals(1, query.getPage());
        Assert.assertEquals(20, query.getPageSize());
    }

    @Test
    public void getPagedStats_WithNoSortParameters_PassesPageRequest() {
        Page pageResult = createMockPage();
        when(blogStatisticsLocalService.getPagedStats(
                any(), eq(new PageRequest(1, 20, null, null))))
                .thenReturn(pageResult);

        PagedResponseDTO result = resourceService.getPagedStats(
                createQuery(EXPLICIT_COMPANY_ID, BLOG_ENTRY_ID, null, null,
                        1, 20, null, null));

        Assert.assertNotNull(result);
        verify(blogStatisticsLocalService).getPagedStats(
                new Filter(EXPLICIT_COMPANY_ID, BLOG_ENTRY_ID, null, null),
                new PageRequest(1, 20, null, null));
    }

    @Test
    public void getPagedStats_WithBothSortParameters_PassesEnums() {
        Page pageResult = createMockPage();
        when(blogStatisticsLocalService.getPagedStats(
                any(), eq(new PageRequest(
                        1, 20, SortAttribute.VIEW_COUNT,
                        SortDirection.ASC)))).thenReturn(pageResult);

        PagedResponseDTO result = resourceService.getPagedStats(
                createQuery(
                        EXPLICIT_COMPANY_ID, BLOG_ENTRY_ID, null, null, 1, 20,
                        SortAttribute.VIEW_COUNT, SortDirection.ASC));

        Assert.assertNotNull(result);
        verify(blogStatisticsLocalService).getPagedStats(
                new Filter(EXPLICIT_COMPANY_ID, BLOG_ENTRY_ID, null, null),
                new PageRequest(
                        1, 20, SortAttribute.VIEW_COUNT, SortDirection.ASC));
    }

    @Test(expected = InvalidSortException.class)
    public void getPagedStats_WithOnlyOneSortParameter_ThrowsCustomException() {
        resourceService.getPagedStats(
                createQuery(
                        EXPLICIT_COMPANY_ID, BLOG_ENTRY_ID, null, null, 1, 20,
                        SortAttribute.VIEW_COUNT, null));
    }

    @Test
    public void getPagedStats_WithNoSortAndExplicitCompanyId_UsesPassedCompanyId() {
        Page pageResult = createMockPage();
        when(blogStatisticsLocalService.getPagedStats(
                any(), eq(new PageRequest(1, 20, null, null))))
                .thenReturn(pageResult);

        PagedResponseDTO result = resourceService.getPagedStats(
                createQuery(EXPLICIT_COMPANY_ID, BLOG_ENTRY_ID, 10L, 500L,
                        1, 20, null, null));

        Assert.assertNotNull(result);
        Filter expectedFilter = new Filter(EXPLICIT_COMPANY_ID, BLOG_ENTRY_ID, 10L, 500L);
        verify(blogStatisticsLocalService).getPagedStats(
                expectedFilter, new PageRequest(1, 20, null, null));
    }

    @Test
    public void getPagedStats_WithNullCompanyId_ResolvesFromCompanyThreadLocal() {
        try (MockedStatic<CompanyThreadLocal> mockedCompanyThreadLocal = mockStatic(CompanyThreadLocal.class)) {
            mockedCompanyThreadLocal.when(CompanyThreadLocal::getCompanyId).thenReturn(DEFAULT_COMPANY_ID);

            Page pageResult = createMockPage();
            when(blogStatisticsLocalService.getPagedStats(
                    any(), eq(new PageRequest(1, 20, null, null))))
                    .thenReturn(pageResult);

            PagedResponseDTO result = resourceService.getPagedStats(
                    createQuery(null, BLOG_ENTRY_ID, 10L, 500L,
                            1, 20, null, null));

            Assert.assertNotNull(result);
            Filter expectedFilter = new Filter(DEFAULT_COMPANY_ID, BLOG_ENTRY_ID, 10L, 500L);
            verify(blogStatisticsLocalService).getPagedStats(
                    expectedFilter, new PageRequest(1, 20, null, null));
        }
    }

    @Test
    public void getPagedStats_WithNullOptionalFilters_PassesFilterWithNulls() {
        try (MockedStatic<CompanyThreadLocal> mockedCompanyThreadLocal = mockStatic(CompanyThreadLocal.class)) {
            mockedCompanyThreadLocal.when(CompanyThreadLocal::getCompanyId).thenReturn(DEFAULT_COMPANY_ID);

            Page pageResult = createMockPage();
            when(blogStatisticsLocalService.getPagedStats(
                    any(), eq(new PageRequest(2, 50, null, null))))
                    .thenReturn(pageResult);

            PagedResponseDTO result = resourceService.getPagedStats(
                    createQuery(null, null, null, null, 2, 50, null, null));

            Assert.assertNotNull(result);
            Filter expectedFilter = new Filter(DEFAULT_COMPANY_ID, null, null, null);
            verify(blogStatisticsLocalService).getPagedStats(
                    expectedFilter, new PageRequest(2, 50, null, null));
        }
    }

    @Test
    public void getPagedStats_WhenPageContainsItems_MapsDtoCorrectly() {
        try (MockedStatic<CompanyThreadLocal> mockedCompanyThreadLocal = mockStatic(CompanyThreadLocal.class)) {
            mockedCompanyThreadLocal.when(CompanyThreadLocal::getCompanyId).thenReturn(DEFAULT_COMPANY_ID);

            BlogStatistics item = createMockBlogStatistics(BLOG_ENTRY_ID, 100L);
            Page pageResult = mock(Page.class);
            List items = List.of(item);
            when(pageResult.items()).thenReturn(items);
            when(pageResult.page()).thenReturn(1);
            when(pageResult.pageSize()).thenReturn(10);
            when(pageResult.totalCount()).thenReturn(1L);

            when(blogStatisticsLocalService.getPagedStats(
                    any(), eq(new PageRequest(1, 10, null, null))))
                    .thenReturn(pageResult);

            PagedResponseDTO result = resourceService.getPagedStats(
                    createQuery(null, BLOG_ENTRY_ID, null, null, 1, 10,
                            null, null));

            Assert.assertNotNull(result);
            verify(blogStatisticsLocalService).getPagedStats(
                    any(), eq(new PageRequest(1, 10, null, null)));
        }
    }

    @Test
    public void getPagedStats_WithSort_PassesSortToLocalService() {
        Page pageResult = createMockPage();
        when(blogStatisticsLocalService.getPagedStats(
                any(), eq(new PageRequest(
                        1, 20, SortAttribute.VIEW_COUNT,
                        SortDirection.ASC)))).thenReturn(pageResult);

        PagedResponseDTO result = resourceService.getPagedStats(
                createQuery(
                        EXPLICIT_COMPANY_ID, BLOG_ENTRY_ID, null, null, 1, 20,
                        SortAttribute.VIEW_COUNT, SortDirection.ASC));

        Assert.assertNotNull(result);
        verify(blogStatisticsLocalService).getPagedStats(
                new Filter(EXPLICIT_COMPANY_ID, BLOG_ENTRY_ID, null, null),
                new PageRequest(
                        1, 20, SortAttribute.VIEW_COUNT, SortDirection.ASC));
    }

    // =========================================================================
    // getViewCount Tests
    // =========================================================================

    @Test
    public void getViewCount_WhenRecordExists_ReturnsMappedDto() {
        try (MockedStatic<CompanyThreadLocal> mockedCompanyThreadLocal = mockStatic(CompanyThreadLocal.class)) {
            mockedCompanyThreadLocal.when(CompanyThreadLocal::getCompanyId).thenReturn(DEFAULT_COMPANY_ID);

            BlogStatistics mockStat = createMockBlogStatistics(BLOG_ENTRY_ID, 42L);
            when(blogStatisticsLocalService.fetchByCompanyIdAndBlogEntryId(DEFAULT_COMPANY_ID, BLOG_ENTRY_ID))
                    .thenReturn(mockStat);

            BlogStatisticsDTO dto = resourceService.getViewCount(BLOG_ENTRY_ID, null);

            Assert.assertNotNull(dto);
            Assert.assertEquals(BLOG_ENTRY_ID, dto.blogEntryId());
            Assert.assertEquals(42L, dto.viewCount());
            verify(blogStatisticsLocalService).fetchByCompanyIdAndBlogEntryId(DEFAULT_COMPANY_ID, BLOG_ENTRY_ID);
        }
    }

    @Test
    public void getViewCount_WithExplicitCompanyId_UsesPassedCompanyId() {
        BlogStatistics mockStat = createMockBlogStatistics(BLOG_ENTRY_ID, 42L);
        when(blogStatisticsLocalService.fetchByCompanyIdAndBlogEntryId(EXPLICIT_COMPANY_ID, BLOG_ENTRY_ID))
                .thenReturn(mockStat);

        BlogStatisticsDTO dto = resourceService.getViewCount(BLOG_ENTRY_ID, EXPLICIT_COMPANY_ID);

        Assert.assertNotNull(dto);
        verify(blogStatisticsLocalService).fetchByCompanyIdAndBlogEntryId(EXPLICIT_COMPANY_ID, BLOG_ENTRY_ID);
    }

    @Test
    public void getViewCount_WhenRecordNotFound_ReturnsNullOrEmpty() {
        try (MockedStatic<CompanyThreadLocal> mockedCompanyThreadLocal = mockStatic(CompanyThreadLocal.class)) {
            mockedCompanyThreadLocal.when(CompanyThreadLocal::getCompanyId).thenReturn(DEFAULT_COMPANY_ID);

            when(blogStatisticsLocalService.fetchByCompanyIdAndBlogEntryId(DEFAULT_COMPANY_ID, BLOG_ENTRY_ID))
                    .thenReturn(null);

            BlogStatisticsDTO dto = resourceService.getViewCount(BLOG_ENTRY_ID, null);

            Assert.assertNull(dto);
            verify(blogStatisticsLocalService).fetchByCompanyIdAndBlogEntryId(DEFAULT_COMPANY_ID, BLOG_ENTRY_ID);
        }
    }

    // =========================================================================
    // incrementViewCount Tests
    // =========================================================================

    @Test
    public void incrementViewCount_WhenCalled_DelegatesAndReturnsUpdatedDto() {
        BlogStatistics updatedStat = createMockBlogStatistics(BLOG_ENTRY_ID, 16L);
        when(blogStatisticsLocalService.incrementViewCount(EXPLICIT_COMPANY_ID, BLOG_ENTRY_ID))
                .thenReturn(updatedStat);

        BlogStatisticsDTO dto = resourceService.incrementViewCount(BLOG_ENTRY_ID, EXPLICIT_COMPANY_ID);

        Assert.assertNotNull(dto);
        Assert.assertEquals(16L, dto.viewCount());
        verify(blogStatisticsLocalService).incrementViewCount(EXPLICIT_COMPANY_ID, BLOG_ENTRY_ID);
    }

    @Test
    public void incrementViewCount_WithNullCompanyId_ResolvesFromCompanyThreadLocal() {
        try (MockedStatic<CompanyThreadLocal> mockedCompanyThreadLocal = mockStatic(CompanyThreadLocal.class)) {
            mockedCompanyThreadLocal.when(CompanyThreadLocal::getCompanyId).thenReturn(DEFAULT_COMPANY_ID);

            BlogStatistics updatedStat = createMockBlogStatistics(BLOG_ENTRY_ID, 1L);
            when(blogStatisticsLocalService.incrementViewCount(DEFAULT_COMPANY_ID, BLOG_ENTRY_ID))
                    .thenReturn(updatedStat);

            BlogStatisticsDTO dto = resourceService.incrementViewCount(BLOG_ENTRY_ID, null);

            Assert.assertNotNull(dto);
            verify(blogStatisticsLocalService).incrementViewCount(DEFAULT_COMPANY_ID, BLOG_ENTRY_ID);
        }
    }

    // =========================================================================
    // deleteViewCount Tests
    // =========================================================================

    @Test
    public void deleteViewCount_WhenValid_DelegatesToService() throws Exception {
        try (MockedStatic<CompanyThreadLocal> mockedCompanyThreadLocal = mockStatic(CompanyThreadLocal.class)) {
            mockedCompanyThreadLocal.when(CompanyThreadLocal::getCompanyId).thenReturn(DEFAULT_COMPANY_ID);

            resourceService.deleteViewCount(BLOG_ENTRY_ID, null);

            verify(blogStatisticsLocalService).deleteByCompanyIdAndBlogEntryId(DEFAULT_COMPANY_ID, BLOG_ENTRY_ID);
        }
    }

    @Test
    public void deleteViewCount_WithExplicitCompanyId_UsesPassedCompanyId() throws Exception {
        resourceService.deleteViewCount(BLOG_ENTRY_ID, EXPLICIT_COMPANY_ID);

        verify(blogStatisticsLocalService).deleteByCompanyIdAndBlogEntryId(EXPLICIT_COMPANY_ID, BLOG_ENTRY_ID);
    }

    @Test(expected = Exception.class)
    public void deleteViewCount_WhenServiceThrowsException_PropagatesException() throws Exception {
        try (MockedStatic<CompanyThreadLocal> mockedCompanyThreadLocal = mockStatic(CompanyThreadLocal.class)) {
            mockedCompanyThreadLocal.when(CompanyThreadLocal::getCompanyId).thenReturn(DEFAULT_COMPANY_ID);

            doThrow(new Exception("Record not found"))
                    .when(blogStatisticsLocalService).deleteByCompanyIdAndBlogEntryId(DEFAULT_COMPANY_ID, BLOG_ENTRY_ID);

            resourceService.deleteViewCount(BLOG_ENTRY_ID, null);
        }
    }

    // =========================================================================
    // deleteAll Tests
    // =========================================================================

    @Test
    public void deleteAll_WhenCalled_DelegatesToService() {
        resourceService.deleteAll();

        verify(blogStatisticsLocalService).deleteAllStats();
    }

    // =========================================================================
    // Helper Methods
    // =========================================================================

    private BlogStatistics createMockBlogStatistics(long blogEntryId, long viewCount) {
        BlogStatistics stat = mock(BlogStatistics.class);
        when(stat.getBlogEntryId()).thenReturn(blogEntryId);
        when(stat.getViewCount()).thenReturn(viewCount);
        when(stat.getCompanyId()).thenReturn(DEFAULT_COMPANY_ID);
        return stat;
    }

    private GetAllViewCountsQuery createQuery(
            Long companyId, Long blogEntryId, Long minViews, Long maxViews,
            int page, int pageSize, SortAttribute sortAttribute,
            SortDirection sortDirection) {

        GetAllViewCountsQuery query = new GetAllViewCountsQuery();
        query.setCompanyId(companyId);
        query.setBlogEntryId(blogEntryId);
        query.setMinViews(minViews);
        query.setMaxViews(maxViews);
        query.setPage(page);
        query.setPageSize(pageSize);
        query.setSortAttribute(sortAttribute);
        query.setSortDirection(sortDirection);

        return query;
    }

    private Page createMockPage() {
        Page page = mock(Page.class);
        when(page.items()).thenReturn(Collections.emptyList());
        when(page.page()).thenReturn(1);
        when(page.pageSize()).thenReturn(20);
        when(page.totalCount()).thenReturn(0L);
        return page;
    }
}