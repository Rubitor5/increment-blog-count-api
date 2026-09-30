package com.example.blogstatistics.service.impl;

import com.example.blogstatistics.dto.Filter;
import com.example.blogstatistics.dto.Page;
import com.example.blogstatistics.dto.PageRequest;
import com.example.blogstatistics.dto.SortAttribute;
import com.example.blogstatistics.dto.SortDirection;
import com.example.blogstatistics.exception.NoSuchBlogStatisticsException;
import com.example.blogstatistics.model.BlogStatistics;
import com.example.blogstatistics.service.persistence.BlogStatisticsPersistence;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Order;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BlogStatisticsLocalServiceImplTest {

    private static final long COMPANY_ID = 10154L;
    private static final long BLOG_ENTRY_ID = 2024L;
    private static final long STAT_ID = 5001L;

    @Mock
    private BlogStatisticsPersistence blogStatisticsPersistence;

    @Mock
    private CounterLocalService counterLocalService;

    @Spy
    @InjectMocks
    private BlogStatisticsLocalServiceImpl blogStatisticsLocalService;

    // =========================================================================
    // fetchByCompanyIdAndBlogEntryId Tests
    // =========================================================================

    @Test
    public void fetchByCompanyIdAndBlogEntryId_WhenFound_ReturnsEntity() {
        BlogStatistics expectedStat = mock(BlogStatistics.class);
        when(blogStatisticsPersistence.fetchByCompanyIdBlogEntryId(COMPANY_ID, BLOG_ENTRY_ID))
                .thenReturn(expectedStat);

        BlogStatistics result = blogStatisticsLocalService.fetchByCompanyIdAndBlogEntryId(COMPANY_ID, BLOG_ENTRY_ID);

        assertNotNull(result);
        assertEquals(expectedStat, result);
    }

    @Test
    public void fetchByCompanyIdAndBlogEntryId_WhenNotFound_ReturnsNull() {
        assertNull(blogStatisticsLocalService.fetchByCompanyIdAndBlogEntryId(COMPANY_ID, BLOG_ENTRY_ID));
    }

    // =========================================================================
    // incrementViewCount Tests
    // =========================================================================

    @Test
    public void incrementViewCount_WhenStatDoesNotExist_CreatesNewStat() {
        BlogStatistics newStat = mock(BlogStatistics.class);

        when(counterLocalService.increment(BlogStatistics.class.getName())).thenReturn(STAT_ID);
        when(blogStatisticsPersistence.create(STAT_ID)).thenReturn(newStat);
        when(blogStatisticsPersistence.update(newStat)).thenReturn(newStat);

        BlogStatistics result = blogStatisticsLocalService.incrementViewCount(COMPANY_ID, BLOG_ENTRY_ID);

        assertNotNull(result);
        verify(newStat).setCompanyId(COMPANY_ID);
        verify(newStat).setBlogEntryId(BLOG_ENTRY_ID);
        verify(newStat).setViewCount(1L);
        verify(blogStatisticsPersistence).update(newStat);
    }

    @Test
    public void incrementViewCount_WhenStatExists_IncrementsViewCount() {
        BlogStatistics existingStat = mock(BlogStatistics.class);
        when(existingStat.getViewCount()).thenReturn(5L);
        when(blogStatisticsPersistence.fetchByCompanyIdBlogEntryId(COMPANY_ID, BLOG_ENTRY_ID)).thenReturn(existingStat);
        when(blogStatisticsPersistence.update(existingStat)).thenReturn(existingStat);

        BlogStatistics result = blogStatisticsLocalService.incrementViewCount(COMPANY_ID, BLOG_ENTRY_ID);

        assertNotNull(result);
        verify(counterLocalService, never()).increment(anyString());
        verify(existingStat).setViewCount(6L);
        verify(blogStatisticsPersistence).update(existingStat);
    }

    // =========================================================================
    // deleteByCompanyIdAndBlogEntryId Tests
    // =========================================================================

    @Test
    public void deleteByCompanyIdAndBlogEntryId_WhenExists_RemovesAndReturnsEntity() throws Exception {
        BlogStatistics existingStat = mock(BlogStatistics.class);
        when(blogStatisticsPersistence.fetchByCompanyIdBlogEntryId(COMPANY_ID, BLOG_ENTRY_ID)).thenReturn(existingStat);
        when(blogStatisticsPersistence.remove(existingStat)).thenReturn(existingStat);

        BlogStatistics result = blogStatisticsLocalService.deleteByCompanyIdAndBlogEntryId(COMPANY_ID, BLOG_ENTRY_ID);

        assertNotNull(result);
        verify(blogStatisticsPersistence).remove(existingStat);
    }

    @Test(expected = NoSuchBlogStatisticsException.class)
    public void deleteByCompanyIdAndBlogEntryId_WhenNotFound_ThrowsException() throws Exception {
        blogStatisticsLocalService.deleteByCompanyIdAndBlogEntryId(COMPANY_ID, BLOG_ENTRY_ID);
    }

    // =========================================================================
    // deleteAllStats Tests
    // =========================================================================

    @Test
    public void deleteAllStats_WhenCalled_DelegatesToPersistence() {
        blogStatisticsLocalService.deleteAllStats();
        verify(blogStatisticsPersistence).removeAll();
    }

    // =========================================================================
    // getPagedStats Tests
    // =========================================================================

    @Test
    public void getPagedStats_WithValidPageAndSize_CalculatesPagination() {
        DynamicQuery mockQuery = mock(DynamicQuery.class);
        doReturn(mockQuery).when(blogStatisticsLocalService).buildDynamicQuery(
                any(), nullable(SortAttribute.class),
                nullable(SortDirection.class));

        List items = Collections.singletonList(mock(BlogStatistics.class));
        when(blogStatisticsPersistence.findWithDynamicQuery(eq(mockQuery), eq(0), eq(10))).thenReturn(items);
        when(blogStatisticsPersistence.countWithDynamicQuery(mockQuery)).thenReturn(15L);

        Page page = blogStatisticsLocalService.getPagedStats(
                new Filter(COMPANY_ID, BLOG_ENTRY_ID, 5L, 100L),
                new PageRequest(1, 10, null, null));

        assertNotNull(page);
        assertEquals(1, page.page());
        assertEquals(10, page.pageSize());
        assertEquals(15L, page.totalCount());
        verify(blogStatisticsLocalService).buildDynamicQuery(
                any(), eq(SortAttribute.VIEW_COUNT), eq(SortDirection.DESC));
        verify(blogStatisticsLocalService).buildDynamicQuery(
                any(), isNull(), isNull());
    }

    @Test
    public void getPagedStats_WithInvalidPaginationValues_NormalizesToOne() {
        DynamicQuery mockQuery = mock(DynamicQuery.class);
        doReturn(mockQuery).when(blogStatisticsLocalService).buildDynamicQuery(
                any(), nullable(SortAttribute.class),
                nullable(SortDirection.class));

        when(blogStatisticsPersistence.findWithDynamicQuery(eq(mockQuery), eq(0), eq(1))).thenReturn(Collections.emptyList());
        when(blogStatisticsPersistence.countWithDynamicQuery(mockQuery)).thenReturn(0L);

        Page page = blogStatisticsLocalService.getPagedStats(
                null, new PageRequest(0, -5, null, null));

        assertNotNull(page);
        assertEquals(1, page.page());
        assertEquals(1, page.pageSize());
    }

    @Test
    public void getPagedStats_WithSort_UsesSortForResultsButNotCount() {
        DynamicQuery resultQuery = mock(DynamicQuery.class);
        DynamicQuery countQuery = mock(DynamicQuery.class);
        doReturn(resultQuery).when(blogStatisticsLocalService).buildDynamicQuery(
                any(), eq(SortAttribute.BLOG_ENTRY_ID), eq(SortDirection.ASC));
        doReturn(countQuery).when(blogStatisticsLocalService).buildDynamicQuery(
                any(), isNull(), isNull());

        when(blogStatisticsPersistence.findWithDynamicQuery(
                eq(resultQuery), eq(0), eq(10))).thenReturn(Collections.emptyList());
        when(blogStatisticsPersistence.countWithDynamicQuery(countQuery))
                .thenReturn(0L);

        Page page = blogStatisticsLocalService.getPagedStats(
                new Filter(COMPANY_ID, null, null, null),
                new PageRequest(
                        1, 10, SortAttribute.BLOG_ENTRY_ID,
                        SortDirection.ASC));

        assertNotNull(page);
        verify(blogStatisticsPersistence).findWithDynamicQuery(
                resultQuery, 0, 10);
        verify(blogStatisticsPersistence).countWithDynamicQuery(countQuery);
    }

    @Test
    public void buildDynamicQuery_WithSort_AddsOrder() {
        DynamicQuery mockQuery = mock(DynamicQuery.class);
        Order mockOrder = mock(Order.class);
        doReturn(mockQuery).when(blogStatisticsLocalService).dynamicQuery();

        try (MockedStatic<OrderFactoryUtil> mockedOrderFactory =
                     mockStatic(OrderFactoryUtil.class)) {
            mockedOrderFactory.when(
                    () -> OrderFactoryUtil.desc("viewCount"))
                    .thenReturn(mockOrder);

            blogStatisticsLocalService.buildDynamicQuery(
                    null, SortAttribute.VIEW_COUNT, SortDirection.DESC);

            verify(mockQuery).addOrder(mockOrder);
        }
    }

    @Test
    public void buildDynamicQuery_WithAscendingSort_AddsAscendingOrder() {
        DynamicQuery mockQuery = mock(DynamicQuery.class);
        Order mockOrder = mock(Order.class);
        doReturn(mockQuery).when(blogStatisticsLocalService).dynamicQuery();

        try (MockedStatic<OrderFactoryUtil> mockedOrderFactory =
                     mockStatic(OrderFactoryUtil.class)) {
            mockedOrderFactory.when(
                    () -> OrderFactoryUtil.asc("blogEntryId"))
                    .thenReturn(mockOrder);

            blogStatisticsLocalService.buildDynamicQuery(
                    null, SortAttribute.BLOG_ENTRY_ID, SortDirection.ASC);

            verify(mockQuery).addOrder(mockOrder);
        }
    }
}