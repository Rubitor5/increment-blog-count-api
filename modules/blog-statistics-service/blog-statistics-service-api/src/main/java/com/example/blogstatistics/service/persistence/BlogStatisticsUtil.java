/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.blogstatistics.service.persistence;

import com.example.blogstatistics.model.BlogStatistics;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the blog statistics service. This utility wraps <code>com.example.blogstatistics.service.persistence.impl.BlogStatisticsPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BlogStatisticsPersistence
 * @generated
 */
public class BlogStatisticsUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#cacheResult(List)
	 */
	public static void cacheResult(List<BlogStatistics> blogStatisticses) {
		getPersistence().cacheResult(blogStatisticses);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#cacheResult(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void cacheResult(BlogStatistics blogStatistics) {
		getPersistence().cacheResult(blogStatistics);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(BlogStatistics blogStatistics) {
		getPersistence().clearCache(blogStatistics);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, BlogStatistics> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<BlogStatistics> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<BlogStatistics> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<BlogStatistics> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<BlogStatistics> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static BlogStatistics update(BlogStatistics blogStatistics) {
		return getPersistence().update(blogStatistics);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static BlogStatistics update(
		BlogStatistics blogStatistics, ServiceContext serviceContext) {

		return getPersistence().update(blogStatistics, serviceContext);
	}

	/**
	 * Returns the blog statistics where blogEntryId = &#63; or throws a <code>NoSuchBlogStatisticsException</code> if it could not be found.
	 *
	 * @param blogEntryId the blog entry ID
	 * @return the matching blog statistics
	 * @throws NoSuchBlogStatisticsException if a matching blog statistics could not be found
	 */
	public static BlogStatistics findByEntryId(long blogEntryId)
		throws com.example.blogstatistics.exception.
			NoSuchBlogStatisticsException {

		return getPersistence().findByEntryId(blogEntryId);
	}

	/**
	 * Returns the blog statistics where blogEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param blogEntryId the blog entry ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching blog statistics, or <code>null</code> if a matching blog statistics could not be found
	 */
	public static BlogStatistics fetchByEntryId(
		long blogEntryId, boolean useFinderCache) {

		return getPersistence().fetchByEntryId(blogEntryId, useFinderCache);
	}

	/**
	 * Removes the blog statistics where blogEntryId = &#63; from the database.
	 *
	 * @param blogEntryId the blog entry ID
	 * @return the blog statistics that was removed
	 */
	public static BlogStatistics removeByEntryId(long blogEntryId)
		throws com.example.blogstatistics.exception.
			NoSuchBlogStatisticsException {

		return getPersistence().removeByEntryId(blogEntryId);
	}

	/**
	 * Returns the number of blog statisticses where blogEntryId = &#63;.
	 *
	 * @param blogEntryId the blog entry ID
	 * @return the number of matching blog statisticses
	 */
	public static int countByEntryId(long blogEntryId) {
		return getPersistence().countByEntryId(blogEntryId);
	}

	/**
	 * Returns the blog statistics where companyId = &#63; and blogEntryId = &#63; or throws a <code>NoSuchBlogStatisticsException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param blogEntryId the blog entry ID
	 * @return the matching blog statistics
	 * @throws NoSuchBlogStatisticsException if a matching blog statistics could not be found
	 */
	public static BlogStatistics findByCompanyIdBlogEntryId(
			long companyId, long blogEntryId)
		throws com.example.blogstatistics.exception.
			NoSuchBlogStatisticsException {

		return getPersistence().findByCompanyIdBlogEntryId(
			companyId, blogEntryId);
	}

	/**
	 * Returns the blog statistics where companyId = &#63; and blogEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param blogEntryId the blog entry ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching blog statistics, or <code>null</code> if a matching blog statistics could not be found
	 */
	public static BlogStatistics fetchByCompanyIdBlogEntryId(
		long companyId, long blogEntryId, boolean useFinderCache) {

		return getPersistence().fetchByCompanyIdBlogEntryId(
			companyId, blogEntryId, useFinderCache);
	}

	/**
	 * Removes the blog statistics where companyId = &#63; and blogEntryId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param blogEntryId the blog entry ID
	 * @return the blog statistics that was removed
	 */
	public static BlogStatistics removeByCompanyIdBlogEntryId(
			long companyId, long blogEntryId)
		throws com.example.blogstatistics.exception.
			NoSuchBlogStatisticsException {

		return getPersistence().removeByCompanyIdBlogEntryId(
			companyId, blogEntryId);
	}

	/**
	 * Returns the number of blog statisticses where companyId = &#63; and blogEntryId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param blogEntryId the blog entry ID
	 * @return the number of matching blog statisticses
	 */
	public static int countByCompanyIdBlogEntryId(
		long companyId, long blogEntryId) {

		return getPersistence().countByCompanyIdBlogEntryId(
			companyId, blogEntryId);
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
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching blog statisticses
	 */
	public static List<BlogStatistics> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<BlogStatistics> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first blog statistics in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blog statistics
	 * @throws NoSuchBlogStatisticsException if a matching blog statistics could not be found
	 */
	public static BlogStatistics findByCompanyId_First(
			long companyId, OrderByComparator<BlogStatistics> orderByComparator)
		throws com.example.blogstatistics.exception.
			NoSuchBlogStatisticsException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first blog statistics in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blog statistics, or <code>null</code> if a matching blog statistics could not be found
	 */
	public static BlogStatistics fetchByCompanyId_First(
		long companyId, OrderByComparator<BlogStatistics> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Removes all the blog statisticses where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of blog statisticses where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching blog statisticses
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns all the blog statisticses where viewCount &gt; &#63;.
	 *
	 * @param viewCount the view count
	 * @return the matching blog statisticses
	 */
	public static List<BlogStatistics> findByViewCountGreater(long viewCount) {
		return getPersistence().findByViewCountGreater(viewCount);
	}

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
	public static List<BlogStatistics> findByViewCountGreater(
		long viewCount, int start, int end) {

		return getPersistence().findByViewCountGreater(viewCount, start, end);
	}

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
	public static List<BlogStatistics> findByViewCountGreater(
		long viewCount, int start, int end,
		OrderByComparator<BlogStatistics> orderByComparator) {

		return getPersistence().findByViewCountGreater(
			viewCount, start, end, orderByComparator);
	}

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
	public static List<BlogStatistics> findByViewCountGreater(
		long viewCount, int start, int end,
		OrderByComparator<BlogStatistics> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByViewCountGreater(
			viewCount, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first blog statistics in the ordered set where viewCount &gt; &#63;.
	 *
	 * @param viewCount the view count
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blog statistics
	 * @throws NoSuchBlogStatisticsException if a matching blog statistics could not be found
	 */
	public static BlogStatistics findByViewCountGreater_First(
			long viewCount, OrderByComparator<BlogStatistics> orderByComparator)
		throws com.example.blogstatistics.exception.
			NoSuchBlogStatisticsException {

		return getPersistence().findByViewCountGreater_First(
			viewCount, orderByComparator);
	}

	/**
	 * Returns the first blog statistics in the ordered set where viewCount &gt; &#63;.
	 *
	 * @param viewCount the view count
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blog statistics, or <code>null</code> if a matching blog statistics could not be found
	 */
	public static BlogStatistics fetchByViewCountGreater_First(
		long viewCount, OrderByComparator<BlogStatistics> orderByComparator) {

		return getPersistence().fetchByViewCountGreater_First(
			viewCount, orderByComparator);
	}

	/**
	 * Removes all the blog statisticses where viewCount &gt; &#63; from the database.
	 *
	 * @param viewCount the view count
	 */
	public static void removeByViewCountGreater(long viewCount) {
		getPersistence().removeByViewCountGreater(viewCount);
	}

	/**
	 * Returns the number of blog statisticses where viewCount &gt; &#63;.
	 *
	 * @param viewCount the view count
	 * @return the number of matching blog statisticses
	 */
	public static int countByViewCountGreater(long viewCount) {
		return getPersistence().countByViewCountGreater(viewCount);
	}

	/**
	 * Creates a new blog statistics with the primary key. Does not add the blog statistics to the database.
	 *
	 * @param blogStatisticsId the primary key for the new blog statistics
	 * @return the new blog statistics
	 */
	public static BlogStatistics create(long blogStatisticsId) {
		return getPersistence().create(blogStatisticsId);
	}

	/**
	 * Removes the blog statistics with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param blogStatisticsId the primary key of the blog statistics
	 * @return the blog statistics that was removed
	 * @throws NoSuchBlogStatisticsException if a blog statistics with the primary key could not be found
	 */
	public static BlogStatistics remove(long blogStatisticsId)
		throws com.example.blogstatistics.exception.
			NoSuchBlogStatisticsException {

		return getPersistence().remove(blogStatisticsId);
	}

	public static BlogStatistics updateImpl(BlogStatistics blogStatistics) {
		return getPersistence().updateImpl(blogStatistics);
	}

	/**
	 * Returns the blog statistics with the primary key or throws a <code>NoSuchBlogStatisticsException</code> if it could not be found.
	 *
	 * @param blogStatisticsId the primary key of the blog statistics
	 * @return the blog statistics
	 * @throws NoSuchBlogStatisticsException if a blog statistics with the primary key could not be found
	 */
	public static BlogStatistics findByPrimaryKey(long blogStatisticsId)
		throws com.example.blogstatistics.exception.
			NoSuchBlogStatisticsException {

		return getPersistence().findByPrimaryKey(blogStatisticsId);
	}

	/**
	 * Returns the blog statistics with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param blogStatisticsId the primary key of the blog statistics
	 * @return the blog statistics, or <code>null</code> if a blog statistics with the primary key could not be found
	 */
	public static BlogStatistics fetchByPrimaryKey(long blogStatisticsId) {
		return getPersistence().fetchByPrimaryKey(blogStatisticsId);
	}

	/**
	 * Returns the blog statistics where blogEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param blogEntryId the blog entry ID
	 * @return the matching blog statistics, or <code>null</code> if a matching blog statistics could not be found
	 */
	public static BlogStatistics fetchByEntryId(long blogEntryId) {
		return getPersistence().fetchByEntryId(blogEntryId);
	}

	/**
	 * Returns the blog statistics where companyId = &#63; and blogEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param blogEntryId the blog entry ID
	 * @return the matching blog statistics, or <code>null</code> if a matching blog statistics could not be found
	 */
	public static BlogStatistics fetchByCompanyIdBlogEntryId(
		long companyId, long blogEntryId) {

		return getPersistence().fetchByCompanyIdBlogEntryId(
			companyId, blogEntryId);
	}

	/**
	 * Returns all the blog statisticses where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching blog statisticses
	 */
	public static List<BlogStatistics> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
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
	public static List<BlogStatistics> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
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
	public static List<BlogStatistics> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<BlogStatistics> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	public static BlogStatisticsPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(BlogStatisticsPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile BlogStatisticsPersistence _persistence;

}
// LIFERAY-SERVICE-BUILDER-HASH:-889740958