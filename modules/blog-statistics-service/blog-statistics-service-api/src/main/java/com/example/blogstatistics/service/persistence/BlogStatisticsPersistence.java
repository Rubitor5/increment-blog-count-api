/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.blogstatistics.service.persistence;

import com.example.blogstatistics.exception.NoSuchBlogStatisticsException;
import com.example.blogstatistics.model.BlogStatistics;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the blog statistics service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BlogStatisticsUtil
 * @generated
 */
@ProviderType
public interface BlogStatisticsPersistence
	extends BasePersistence<BlogStatistics> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link BlogStatisticsUtil} to access the blog statistics persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns the blog statistics where blogEntryId = &#63; or throws a <code>NoSuchBlogStatisticsException</code> if it could not be found.
	 *
	 * @param blogEntryId the blog entry ID
	 * @return the matching blog statistics
	 * @throws NoSuchBlogStatisticsException if a matching blog statistics could not be found
	 */
	public BlogStatistics findByEntryId(long blogEntryId)
		throws NoSuchBlogStatisticsException;

	/**
	 * Returns the blog statistics where blogEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param blogEntryId the blog entry ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching blog statistics, or <code>null</code> if a matching blog statistics could not be found
	 */
	public BlogStatistics fetchByEntryId(
		long blogEntryId, boolean useFinderCache);

	/**
	 * Removes the blog statistics where blogEntryId = &#63; from the database.
	 *
	 * @param blogEntryId the blog entry ID
	 * @return the blog statistics that was removed
	 */
	public BlogStatistics removeByEntryId(long blogEntryId)
		throws NoSuchBlogStatisticsException;

	/**
	 * Returns the number of blog statisticses where blogEntryId = &#63;.
	 *
	 * @param blogEntryId the blog entry ID
	 * @return the number of matching blog statisticses
	 */
	public int countByEntryId(long blogEntryId);

	/**
	 * Returns the blog statistics where companyId = &#63; and blogEntryId = &#63; or throws a <code>NoSuchBlogStatisticsException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param blogEntryId the blog entry ID
	 * @return the matching blog statistics
	 * @throws NoSuchBlogStatisticsException if a matching blog statistics could not be found
	 */
	public BlogStatistics findByCompanyIdBlogEntryId(
			long companyId, long blogEntryId)
		throws NoSuchBlogStatisticsException;

	/**
	 * Returns the blog statistics where companyId = &#63; and blogEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param blogEntryId the blog entry ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching blog statistics, or <code>null</code> if a matching blog statistics could not be found
	 */
	public BlogStatistics fetchByCompanyIdBlogEntryId(
		long companyId, long blogEntryId, boolean useFinderCache);

	/**
	 * Removes the blog statistics where companyId = &#63; and blogEntryId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param blogEntryId the blog entry ID
	 * @return the blog statistics that was removed
	 */
	public BlogStatistics removeByCompanyIdBlogEntryId(
			long companyId, long blogEntryId)
		throws NoSuchBlogStatisticsException;

	/**
	 * Returns the number of blog statisticses where companyId = &#63; and blogEntryId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param blogEntryId the blog entry ID
	 * @return the number of matching blog statisticses
	 */
	public int countByCompanyIdBlogEntryId(long companyId, long blogEntryId);

	/**
	 * Returns an ordered range of all the blog statisticses where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.example.blogstatistics.model.impl.BlogStatisticsModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of blog statisticses
	 * @param end the upper bound of the range of blog statisticses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching blog statisticses
	 */
	public java.util.List<BlogStatistics> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogStatistics>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first blog statistics in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blog statistics
	 * @throws NoSuchBlogStatisticsException if a matching blog statistics could not be found
	 */
	public BlogStatistics findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<BlogStatistics>
				orderByComparator)
		throws NoSuchBlogStatisticsException;

	/**
	 * Returns the first blog statistics in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blog statistics, or <code>null</code> if a matching blog statistics could not be found
	 */
	public BlogStatistics fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<BlogStatistics>
			orderByComparator);

	/**
	 * Removes all the blog statisticses where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of blog statisticses where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching blog statisticses
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns all the blog statisticses where viewCount &gt; &#63;.
	 *
	 * @param viewCount the view count
	 * @return the matching blog statisticses
	 */
	public java.util.List<BlogStatistics> findByViewCountGreater(
		long viewCount);

	/**
	 * Returns a range of all the blog statisticses where viewCount &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.example.blogstatistics.model.impl.BlogStatisticsModelImpl</code>.
	 * </p>
	 *
	 * @param viewCount the view count
	 * @param start the lower bound of the range of blog statisticses
	 * @param end the upper bound of the range of blog statisticses (not inclusive)
	 * @return the range of matching blog statisticses
	 */
	public java.util.List<BlogStatistics> findByViewCountGreater(
		long viewCount, int start, int end);

	/**
	 * Returns an ordered range of all the blog statisticses where viewCount &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.example.blogstatistics.model.impl.BlogStatisticsModelImpl</code>.
	 * </p>
	 *
	 * @param viewCount the view count
	 * @param start the lower bound of the range of blog statisticses
	 * @param end the upper bound of the range of blog statisticses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blog statisticses
	 */
	public java.util.List<BlogStatistics> findByViewCountGreater(
		long viewCount, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogStatistics>
			orderByComparator);

	/**
	 * Returns an ordered range of all the blog statisticses where viewCount &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.example.blogstatistics.model.impl.BlogStatisticsModelImpl</code>.
	 * </p>
	 *
	 * @param viewCount the view count
	 * @param start the lower bound of the range of blog statisticses
	 * @param end the upper bound of the range of blog statisticses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching blog statisticses
	 */
	public java.util.List<BlogStatistics> findByViewCountGreater(
		long viewCount, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogStatistics>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first blog statistics in the ordered set where viewCount &gt; &#63;.
	 *
	 * @param viewCount the view count
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blog statistics
	 * @throws NoSuchBlogStatisticsException if a matching blog statistics could not be found
	 */
	public BlogStatistics findByViewCountGreater_First(
			long viewCount,
			com.liferay.portal.kernel.util.OrderByComparator<BlogStatistics>
				orderByComparator)
		throws NoSuchBlogStatisticsException;

	/**
	 * Returns the first blog statistics in the ordered set where viewCount &gt; &#63;.
	 *
	 * @param viewCount the view count
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blog statistics, or <code>null</code> if a matching blog statistics could not be found
	 */
	public BlogStatistics fetchByViewCountGreater_First(
		long viewCount,
		com.liferay.portal.kernel.util.OrderByComparator<BlogStatistics>
			orderByComparator);

	/**
	 * Removes all the blog statisticses where viewCount &gt; &#63; from the database.
	 *
	 * @param viewCount the view count
	 */
	public void removeByViewCountGreater(long viewCount);

	/**
	 * Returns the number of blog statisticses where viewCount &gt; &#63;.
	 *
	 * @param viewCount the view count
	 * @return the number of matching blog statisticses
	 */
	public int countByViewCountGreater(long viewCount);

	/**
	 * Creates a new blog statistics with the primary key. Does not add the blog statistics to the database.
	 *
	 * @param blogStatisticsId the primary key for the new blog statistics
	 * @return the new blog statistics
	 */
	public BlogStatistics create(long blogStatisticsId);

	/**
	 * Removes the blog statistics with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param blogStatisticsId the primary key of the blog statistics
	 * @return the blog statistics that was removed
	 * @throws NoSuchBlogStatisticsException if a blog statistics with the primary key could not be found
	 */
	public BlogStatistics remove(long blogStatisticsId)
		throws NoSuchBlogStatisticsException;

	public BlogStatistics updateImpl(BlogStatistics blogStatistics);

	/**
	 * Returns the blog statistics with the primary key or throws a <code>NoSuchBlogStatisticsException</code> if it could not be found.
	 *
	 * @param blogStatisticsId the primary key of the blog statistics
	 * @return the blog statistics
	 * @throws NoSuchBlogStatisticsException if a blog statistics with the primary key could not be found
	 */
	public BlogStatistics findByPrimaryKey(long blogStatisticsId)
		throws NoSuchBlogStatisticsException;

	/**
	 * Returns the blog statistics with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param blogStatisticsId the primary key of the blog statistics
	 * @return the blog statistics, or <code>null</code> if a blog statistics with the primary key could not be found
	 */
	public BlogStatistics fetchByPrimaryKey(long blogStatisticsId);

	/**
	 * Returns the blog statistics where blogEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param blogEntryId the blog entry ID
	 * @return the matching blog statistics, or <code>null</code> if a matching blog statistics could not be found
	 */
	public default BlogStatistics fetchByEntryId(long blogEntryId) {
		return fetchByEntryId(blogEntryId, true);
	}

	/**
	 * Returns the blog statistics where companyId = &#63; and blogEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param blogEntryId the blog entry ID
	 * @return the matching blog statistics, or <code>null</code> if a matching blog statistics could not be found
	 */
	public default BlogStatistics fetchByCompanyIdBlogEntryId(
		long companyId, long blogEntryId) {

		return fetchByCompanyIdBlogEntryId(companyId, blogEntryId, true);
	}

	/**
	 * Returns all the blog statisticses where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching blog statisticses
	 */
	public default java.util.List<BlogStatistics> findByCompanyId(
		long companyId) {

		return findByCompanyId(
			companyId, com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the blog statisticses where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.example.blogstatistics.model.impl.BlogStatisticsModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of blog statisticses
	 * @param end the upper bound of the range of blog statisticses (not inclusive)
	 * @return the range of matching blog statisticses
	 */
	public default java.util.List<BlogStatistics> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the blog statisticses where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.example.blogstatistics.model.impl.BlogStatisticsModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of blog statisticses
	 * @param end the upper bound of the range of blog statisticses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blog statisticses
	 */
	public default java.util.List<BlogStatistics> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogStatistics>
			orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-2088920231