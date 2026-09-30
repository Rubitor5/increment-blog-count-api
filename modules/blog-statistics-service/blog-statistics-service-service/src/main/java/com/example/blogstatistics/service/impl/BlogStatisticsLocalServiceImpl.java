/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.blogstatistics.service.impl;

import com.example.blogstatistics.dto.Filter;
import com.example.blogstatistics.dto.Page;
import com.example.blogstatistics.exception.NoSuchBlogStatisticsException;
import com.example.blogstatistics.model.BlogStatistics;
import com.example.blogstatistics.service.base.BlogStatisticsLocalServiceBaseImpl;

import com.liferay.portal.aop.AopService;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.transaction.Transactional;
import org.osgi.service.component.annotations.Component;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.example.blogstatistics.model.BlogStatistics",
	service = AopService.class
)
public class BlogStatisticsLocalServiceImpl
	extends BlogStatisticsLocalServiceBaseImpl {

	public Page getPagedStats(Filter filter, int page, int pageSize) {
		int validatedPage = Math.max(1, page);
		int validatedPageSize = Math.max(1, pageSize);

		int start = (validatedPage - 1) * validatedPageSize;
		int end = start + validatedPageSize;

		DynamicQuery query = buildDynamicQuery(filter);
		List<BlogStatistics> items = blogStatisticsPersistence.findWithDynamicQuery(query, start, end);

		DynamicQuery countQuery = buildDynamicQuery(filter);
		long totalCount = blogStatisticsPersistence.countWithDynamicQuery(countQuery);

		return new Page(items, validatedPage, validatedPageSize, totalCount);
	}

	public BlogStatistics fetchByCompanyIdAndBlogEntryId(long companyId, long blogEntryId) {
		return blogStatisticsPersistence.fetchByCompanyIdBlogEntryId(companyId, blogEntryId);
	}

	@Transactional
	public BlogStatistics incrementViewCount(long companyId, long blogEntryId) {
		BlogStatistics stat = blogStatisticsPersistence.fetchByCompanyIdBlogEntryId(companyId, blogEntryId);
		Date now = new Date();

		if (stat == null) {
			long blogStatisticsId = counterLocalService.increment(BlogStatistics.class.getName());
			stat = blogStatisticsPersistence.create(blogStatisticsId);
			stat.setCompanyId(companyId);
			stat.setBlogEntryId(blogEntryId);
			stat.setViewCount(1L);
			stat.setCreateDate(now);
			stat.setModifiedDate(now);
		} else {
			stat.setViewCount(stat.getViewCount() + 1);
			stat.setModifiedDate(now);
		}

		return blogStatisticsPersistence.update(stat);
	}

	@Transactional
	public BlogStatistics deleteByCompanyIdAndBlogEntryId(long companyId, long blogEntryId) throws PortalException {
		BlogStatistics stat = blogStatisticsPersistence.fetchByCompanyIdBlogEntryId(companyId, blogEntryId);
		if (stat == null) {
			throw new NoSuchBlogStatisticsException(
					"No statistics found for companyId " + companyId + " and blogEntryId " + blogEntryId);
		}
		return blogStatisticsPersistence.remove(stat);
	}

	private DynamicQuery buildDynamicQuery(Filter filter) {
		DynamicQuery dynamicQuery = dynamicQuery();

		if (filter == null) {
			return dynamicQuery;
		}

		if (filter.getCompanyId() != null && filter.getCompanyId() > 0) {
			dynamicQuery.add(PropertyFactoryUtil.forName("companyId").eq(filter.getCompanyId()));
		}

		if (filter.getBlogEntryId() != null && filter.getBlogEntryId() > 0) {
			dynamicQuery.add(PropertyFactoryUtil.forName("blogEntryId").eq(filter.getBlogEntryId()));
		}

		if (filter.getMinViews() != null && filter.getMinViews() >= 0) {
			dynamicQuery.add(PropertyFactoryUtil.forName("viewCount").ge(filter.getMinViews()));
		}

		if (filter.getMaxViews() != null && filter.getMaxViews() >= 0) {
			dynamicQuery.add(PropertyFactoryUtil.forName("viewCount").le(filter.getMaxViews()));
		}

		return dynamicQuery;
	}

}