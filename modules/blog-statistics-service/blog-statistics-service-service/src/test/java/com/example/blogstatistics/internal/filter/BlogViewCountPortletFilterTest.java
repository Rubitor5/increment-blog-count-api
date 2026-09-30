package com.example.blogstatistics.internal.filter;

import com.example.blogstatistics.service.BlogStatisticsLocalService;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsUtil;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.filter.FilterChain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BlogViewCountPortletFilterTest {

    private static final long COMPANY_ID = 20101L;
    private static final long GROUP_ID = 30101L;
    private static final long ENTRY_ID = 40101L;
    private static final String URL_TITLE = "sample-blog-post";

    @Mock
    private BlogStatisticsLocalService _blogStatisticsLocalService;

    @Mock
    private BlogsEntryLocalService _blogsEntryLocalService;

    @Mock
    private RenderRequest renderRequest;

    @Mock
    private RenderResponse renderResponse;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private BlogViewCountPortletFilter filter;

    private MockedStatic<ParamUtil> mockedParamUtil;
    private MockedStatic<PortalUtil> mockedPortalUtil;

    @Before
    public void setUp() {
        // Prevent ParamUtil NPE by setting a dummy Props instance on PropsUtil
        PropsUtil.setProps(mock(Props.class));

        mockedParamUtil = mockStatic(ParamUtil.class);
        mockedPortalUtil = mockStatic(PortalUtil.class);

        mockedPortalUtil.when(() -> PortalUtil.getCompanyId(renderRequest)).thenReturn(COMPANY_ID);
        mockedPortalUtil.when(() -> PortalUtil.getScopeGroupId(renderRequest)).thenReturn(GROUP_ID);
    }

    @After
    public void tearDown() {
        if (mockedParamUtil != null) mockedParamUtil.close();
        if (mockedPortalUtil != null) mockedPortalUtil.close();
    }

    // =========================================================================
    // Direct entryId Tests
    // =========================================================================

    @Test
    public void doFilter_WithValidEntryId_IncrementsViewCountAndContinuesChain() throws Exception {
        mockedParamUtil.when(() -> ParamUtil.getLong(renderRequest, "entryId")).thenReturn(ENTRY_ID);

        filter.doFilter(renderRequest, renderResponse, filterChain);

        verify(_blogStatisticsLocalService).incrementViewCount(COMPANY_ID, ENTRY_ID);
        verify(_blogsEntryLocalService, never()).fetchEntry(anyLong(), anyString());
        verify(filterChain).doFilter(renderRequest, renderResponse);
    }

    // =========================================================================
    // urlTitle Resolution Tests
    // =========================================================================

    @Test
    public void doFilter_WithUrlTitle_ResolvesEntryAndIncrementsViewCount() throws Exception {
        mockedParamUtil.when(() -> ParamUtil.getLong(renderRequest, "entryId")).thenReturn(0L);
        mockedParamUtil.when(() -> ParamUtil.getString(renderRequest, "urlTitle")).thenReturn(URL_TITLE);

        BlogsEntry mockEntry = mock(BlogsEntry.class);
        when(mockEntry.getEntryId()).thenReturn(ENTRY_ID);
        when(_blogsEntryLocalService.fetchEntry(GROUP_ID, URL_TITLE)).thenReturn(mockEntry);

        filter.doFilter(renderRequest, renderResponse, filterChain);

        verify(_blogsEntryLocalService).fetchEntry(GROUP_ID, URL_TITLE);
        verify(_blogStatisticsLocalService).incrementViewCount(COMPANY_ID, ENTRY_ID);
        verify(filterChain).doFilter(renderRequest, renderResponse);
    }

    @Test
    public void doFilter_WhenUrlTitleNotFound_DoesNotIncrementViewCount() throws Exception {
        mockedParamUtil.when(() -> ParamUtil.getLong(renderRequest, "entryId")).thenReturn(0L);
        mockedParamUtil.when(() -> ParamUtil.getString(renderRequest, "urlTitle")).thenReturn(URL_TITLE);
        when(_blogsEntryLocalService.fetchEntry(GROUP_ID, URL_TITLE)).thenReturn(null);

        filter.doFilter(renderRequest, renderResponse, filterChain);

        verify(_blogStatisticsLocalService, never()).incrementViewCount(anyLong(), anyLong());
        verify(filterChain).doFilter(renderRequest, renderResponse);
    }

    // =========================================================================
    // Exception & Edge Case Tests
    // =========================================================================

    @Test
    public void doFilter_WhenNoEntryParamsProvided_SkipsIncrementAndContinuesChain() throws Exception {
        mockedParamUtil.when(() -> ParamUtil.getLong(renderRequest, "entryId")).thenReturn(0L);
        mockedParamUtil.when(() -> ParamUtil.getString(renderRequest, "urlTitle")).thenReturn(null);

        filter.doFilter(renderRequest, renderResponse, filterChain);

        verify(_blogStatisticsLocalService, never()).incrementViewCount(anyLong(), anyLong());
        verify(filterChain).doFilter(renderRequest, renderResponse);
    }

    @Test
    public void doFilter_WhenExceptionThrown_CatchesErrorAndContinuesChain() throws Exception {
        mockedParamUtil.when(() -> ParamUtil.getLong(renderRequest, "entryId")).thenReturn(ENTRY_ID);
        doThrow(new RuntimeException("Database error"))
                .when(_blogStatisticsLocalService).incrementViewCount(COMPANY_ID, ENTRY_ID);

        filter.doFilter(renderRequest, renderResponse, filterChain);

        verify(filterChain).doFilter(renderRequest, renderResponse);
    }
}