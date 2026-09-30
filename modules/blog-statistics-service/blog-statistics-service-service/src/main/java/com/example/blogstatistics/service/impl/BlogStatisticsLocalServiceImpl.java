/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.blogstatistics.service.impl;

import com.example.blogstatistics.dto.Filter;
import com.example.blogstatistics.dto.Page;
import com.example.blogstatistics.dto.PageRequest;
import com.example.blogstatistics.dto.SortAttribute;
import com.example.blogstatistics.dto.SortDirection;
import com.example.blogstatistics.exception.NoSuchBlogStatisticsException;
import com.example.blogstatistics.model.BlogStatistics;
import com.example.blogstatistics.service.base.BlogStatisticsLocalServiceBaseImpl;

import com.liferay.portal.aop.AopService;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.transaction.Transactional;
import org.osgi.service.component.annotations.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.example.blogstatistics.model.BlogStatistics",
	service = AopService.class
)
public class BlogStatisticsLocalServiceImpl
	extends BlogStatisticsLocalServiceBaseImpl {

	public Page getPagedStats(
		Filter filter, PageRequest pageRequest) {

		int validatedPage = Math.max(1, pageRequest.page());
		int validatedPageSize = Math.max(1, pageRequest.pageSize());

		int start = (validatedPage - 1) * validatedPageSize;
		int end = start + validatedPageSize;

		SortAttribute resolvedSortAttribute = Optional.ofNullable(
			pageRequest.sortAttribute()).orElse(SortAttribute.VIEW_COUNT);
		SortDirection resolvedSortDirection = Optional.ofNullable(
			pageRequest.sortDirection()).orElse(SortDirection.DESC);

		DynamicQuery query = buildDynamicQuery(
			filter, resolvedSortAttribute, resolvedSortDirection);
		List<BlogStatistics> items = blogStatisticsPersistence.findWithDynamicQuery(query, start, end);

		DynamicQuery countQuery = buildDynamicQuery(filter, null, null);
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
	public void deleteAllStats() {
		blogStatisticsPersistence.removeAll();
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

	DynamicQuery buildDynamicQuery(
		Filter filter, SortAttribute sortAttribute,
		SortDirection sortDirection) {

		DynamicQuery dynamicQuery = dynamicQuery();

		if (filter != null) {
			if (filter.companyId() != null && filter.companyId() > 0) {
				dynamicQuery.add(PropertyFactoryUtil.forName("companyId").eq(filter.companyId()));
			}

			if (filter.blogEntryId() != null && filter.blogEntryId() > 0) {
				dynamicQuery.add(PropertyFactoryUtil.forName("blogEntryId").eq(filter.blogEntryId()));
			}

			if (filter.minViews() != null && filter.minViews() >= 0) {
				dynamicQuery.add(PropertyFactoryUtil.forName("viewCount").ge(filter.minViews()));
			}

			if (filter.maxViews() != null && filter.maxViews() >= 0) {
				dynamicQuery.add(PropertyFactoryUtil.forName("viewCount").le(filter.maxViews()));
			}
		}

		Optional.ofNullable(sortAttribute).ifPresent(
			attribute -> {
			if (sortDirection == SortDirection.ASC) {
				dynamicQuery.addOrder(
					OrderFactoryUtil.asc(attribute.getFieldName()));
			}
			else {
				dynamicQuery.addOrder(
					OrderFactoryUtil.desc(attribute.getFieldName()));
			}
			});

		return dynamicQuery;
	}

}