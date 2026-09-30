/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.blogstatistics.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link BlogStatisticsLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see BlogStatisticsLocalService
 * @generated
 */
public class BlogStatisticsLocalServiceWrapper
	implements BlogStatisticsLocalService,
			   ServiceWrapper<BlogStatisticsLocalService> {

	public BlogStatisticsLocalServiceWrapper() {
		this(null);
	}

	public BlogStatisticsLocalServiceWrapper(
		BlogStatisticsLocalService blogStatisticsLocalService) {

		_blogStatisticsLocalService = blogStatisticsLocalService;
	}

	/**
	 * Adds the blog statistics to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BlogStatisticsLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param blogStatistics the blog statistics
	 * @return the blog statistics that was added
	 */
	@Override
	public com.example.blogstatistics.model.BlogStatistics addBlogStatistics(
		com.example.blogstatistics.model.BlogStatistics blogStatistics) {

		return _blogStatisticsLocalService.addBlogStatistics(blogStatistics);
	}

	/**
	 * Creates a new blog statistics with the primary key. Does not add the blog statistics to the database.
	 *
	 * @param blogStatisticsId the primary key for the new blog statistics
	 * @return the new blog statistics
	 */
	@Override
	public com.example.blogstatistics.model.BlogStatistics createBlogStatistics(
		long blogStatisticsId) {

		return _blogStatisticsLocalService.createBlogStatistics(
			blogStatisticsId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _blogStatisticsLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the blog statistics from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BlogStatisticsLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param blogStatistics the blog statistics
	 * @return the blog statistics that was removed
	 */
	@Override
	public com.example.blogstatistics.model.BlogStatistics deleteBlogStatistics(
		com.example.blogstatistics.model.BlogStatistics blogStatistics) {

		return _blogStatisticsLocalService.deleteBlogStatistics(blogStatistics);
	}

	/**
	 * Deletes the blog statistics with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BlogStatisticsLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param blogStatisticsId the primary key of the blog statistics
	 * @return the blog statistics that was removed
	 * @throws PortalException if a blog statistics with the primary key could not be found
	 */
	@Override
	public com.example.blogstatistics.model.BlogStatistics deleteBlogStatistics(
			long blogStatisticsId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _blogStatisticsLocalService.deleteBlogStatistics(
			blogStatisticsId);
	}

	@Override
	public com.example.blogstatistics.model.BlogStatistics
			deleteByCompanyIdAndBlogEntryId(long companyId, long blogEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _blogStatisticsLocalService.deleteByCompanyIdAndBlogEntryId(
			companyId, blogEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _blogStatisticsLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _blogStatisticsLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _blogStatisticsLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _blogStatisticsLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _blogStatisticsLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.example.blogstatistics.model.impl.BlogStatisticsModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _blogStatisticsLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.example.blogstatistics.model.impl.BlogStatisticsModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _blogStatisticsLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _blogStatisticsLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _blogStatisticsLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.example.blogstatistics.model.BlogStatistics fetchBlogStatistics(
		long blogStatisticsId) {

		return _blogStatisticsLocalService.fetchBlogStatistics(
			blogStatisticsId);
	}

	@Override
	public com.example.blogstatistics.model.BlogStatistics
		fetchByCompanyIdAndBlogEntryId(long companyId, long blogEntryId) {

		return _blogStatisticsLocalService.fetchByCompanyIdAndBlogEntryId(
			companyId, blogEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _blogStatisticsLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the blog statistics with the primary key.
	 *
	 * @param blogStatisticsId the primary key of the blog statistics
	 * @return the blog statistics
	 * @throws PortalException if a blog statistics with the primary key could not be found
	 */
	@Override
	public com.example.blogstatistics.model.BlogStatistics getBlogStatistics(
			long blogStatisticsId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _blogStatisticsLocalService.getBlogStatistics(blogStatisticsId);
	}

	/**
	 * Returns a range of all the blog statisticses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.example.blogstatistics.model.impl.BlogStatisticsModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of blog statisticses
	 * @param end the upper bound of the range of blog statisticses (not inclusive)
	 * @return the range of blog statisticses
	 */
	@Override
	public java.util.List<com.example.blogstatistics.model.BlogStatistics>
		getBlogStatisticses(int start, int end) {

		return _blogStatisticsLocalService.getBlogStatisticses(start, end);
	}

	/**
	 * Returns the number of blog statisticses.
	 *
	 * @return the number of blog statisticses
	 */
	@Override
	public int getBlogStatisticsesCount() {
		return _blogStatisticsLocalService.getBlogStatisticsesCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _blogStatisticsLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _blogStatisticsLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.example.blogstatistics.dto.Page getPagedStats(
		com.example.blogstatistics.dto.Filter filter, int page, int pageSize) {

		return _blogStatisticsLocalService.getPagedStats(
			filter, page, pageSize);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _blogStatisticsLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.example.blogstatistics.model.BlogStatistics incrementViewCount(
		long companyId, long blogEntryId) {

		return _blogStatisticsLocalService.incrementViewCount(
			companyId, blogEntryId);
	}

	/**
	 * Updates the blog statistics in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BlogStatisticsLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param blogStatistics the blog statistics
	 * @return the blog statistics that was updated
	 */
	@Override
	public com.example.blogstatistics.model.BlogStatistics updateBlogStatistics(
		com.example.blogstatistics.model.BlogStatistics blogStatistics) {

		return _blogStatisticsLocalService.updateBlogStatistics(blogStatistics);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _blogStatisticsLocalService.getBasePersistence();
	}

	@Override
	public BlogStatisticsLocalService getWrappedService() {
		return _blogStatisticsLocalService;
	}

	@Override
	public void setWrappedService(
		BlogStatisticsLocalService blogStatisticsLocalService) {

		_blogStatisticsLocalService = blogStatisticsLocalService;
	}

	private BlogStatisticsLocalService _blogStatisticsLocalService;

}
// LIFERAY-SERVICE-BUILDER-HASH:-2075220552