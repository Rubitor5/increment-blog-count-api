/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.blogstatistics.service.persistence.impl;

import com.example.blogstatistics.exception.NoSuchBlogStatisticsException;
import com.example.blogstatistics.model.BlogStatistics;
import com.example.blogstatistics.model.BlogStatisticsTable;
import com.example.blogstatistics.model.impl.BlogStatisticsImpl;
import com.example.blogstatistics.model.impl.BlogStatisticsModelImpl;
import com.example.blogstatistics.service.persistence.BlogStatisticsPersistence;
import com.example.blogstatistics.service.persistence.BlogStatisticsUtil;
import com.example.blogstatistics.service.persistence.impl.constants.BlogStatsPersistenceConstants;

import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.service.persistence.impl.CollectionPersistenceFinder;
import com.liferay.portal.kernel.service.persistence.impl.FinderColumn;
import com.liferay.portal.kernel.service.persistence.impl.UniquePersistenceFinder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the blog statistics service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = BlogStatisticsPersistence.class)
public class BlogStatisticsPersistenceImpl
	extends BasePersistenceImpl<BlogStatistics, NoSuchBlogStatisticsException>
	implements BlogStatisticsPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>BlogStatisticsUtil</code> to access the blog statistics persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		BlogStatisticsImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private UniquePersistenceFinder
		<BlogStatistics, NoSuchBlogStatisticsException>
			_uniquePersistenceFinderByEntryId;

	/**
	 * Returns the blog statistics where blogEntryId = &#63; or throws a <code>NoSuchBlogStatisticsException</code> if it could not be found.
	 *
	 * @param blogEntryId the blog entry ID
	 * @return the matching blog statistics
	 * @throws NoSuchBlogStatisticsException if a matching blog statistics could not be found
	 */
	@Override
	public BlogStatistics findByEntryId(long blogEntryId)
		throws NoSuchBlogStatisticsException {

		return _uniquePersistenceFinderByEntryId.find(
			finderCache, new Object[] {blogEntryId});
	}

	/**
	 * Returns the blog statistics where blogEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param blogEntryId the blog entry ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching blog statistics, or <code>null</code> if a matching blog statistics could not be found
	 */
	@Override
	public BlogStatistics fetchByEntryId(
		long blogEntryId, boolean useFinderCache) {

		return _uniquePersistenceFinderByEntryId.fetch(
			finderCache, new Object[] {blogEntryId}, useFinderCache);
	}

	/**
	 * Removes the blog statistics where blogEntryId = &#63; from the database.
	 *
	 * @param blogEntryId the blog entry ID
	 * @return the blog statistics that was removed
	 */
	@Override
	public BlogStatistics removeByEntryId(long blogEntryId)
		throws NoSuchBlogStatisticsException {

		BlogStatistics blogStatistics = findByEntryId(blogEntryId);

		return remove(blogStatistics);
	}

	/**
	 * Returns the number of blog statisticses where blogEntryId = &#63;.
	 *
	 * @param blogEntryId the blog entry ID
	 * @return the number of matching blog statisticses
	 */
	@Override
	public int countByEntryId(long blogEntryId) {
		return _uniquePersistenceFinderByEntryId.count(
			finderCache, new Object[] {blogEntryId});
	}

	private UniquePersistenceFinder
		<BlogStatistics, NoSuchBlogStatisticsException>
			_uniquePersistenceFinderByCompanyIdBlogEntryId;

	/**
	 * Returns the blog statistics where companyId = &#63; and blogEntryId = &#63; or throws a <code>NoSuchBlogStatisticsException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param blogEntryId the blog entry ID
	 * @return the matching blog statistics
	 * @throws NoSuchBlogStatisticsException if a matching blog statistics could not be found
	 */
	@Override
	public BlogStatistics findByCompanyIdBlogEntryId(
			long companyId, long blogEntryId)
		throws NoSuchBlogStatisticsException {

		return _uniquePersistenceFinderByCompanyIdBlogEntryId.find(
			finderCache, new Object[] {companyId, blogEntryId});
	}

	/**
	 * Returns the blog statistics where companyId = &#63; and blogEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param blogEntryId the blog entry ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching blog statistics, or <code>null</code> if a matching blog statistics could not be found
	 */
	@Override
	public BlogStatistics fetchByCompanyIdBlogEntryId(
		long companyId, long blogEntryId, boolean useFinderCache) {

		return _uniquePersistenceFinderByCompanyIdBlogEntryId.fetch(
			finderCache, new Object[] {companyId, blogEntryId}, useFinderCache);
	}

	/**
	 * Removes the blog statistics where companyId = &#63; and blogEntryId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param blogEntryId the blog entry ID
	 * @return the blog statistics that was removed
	 */
	@Override
	public BlogStatistics removeByCompanyIdBlogEntryId(
			long companyId, long blogEntryId)
		throws NoSuchBlogStatisticsException {

		BlogStatistics blogStatistics = findByCompanyIdBlogEntryId(
			companyId, blogEntryId);

		return remove(blogStatistics);
	}

	/**
	 * Returns the number of blog statisticses where companyId = &#63; and blogEntryId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param blogEntryId the blog entry ID
	 * @return the number of matching blog statisticses
	 */
	@Override
	public int countByCompanyIdBlogEntryId(long companyId, long blogEntryId) {
		return _uniquePersistenceFinderByCompanyIdBlogEntryId.count(
			finderCache, new Object[] {companyId, blogEntryId});
	}

	private CollectionPersistenceFinder
		<BlogStatistics, NoSuchBlogStatisticsException>
			_collectionPersistenceFinderByCompanyId;

	/**
	 * Returns an ordered range of all the blog statisticses where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlogStatisticsModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of blog statisticses
	 * @param end the upper bound of the range of blog statisticses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching blog statisticses
	 */
	@Override
	public List<BlogStatistics> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<BlogStatistics> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByCompanyId.find(
			finderCache, new Object[] {companyId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first blog statistics in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blog statistics
	 * @throws NoSuchBlogStatisticsException if a matching blog statistics could not be found
	 */
	@Override
	public BlogStatistics findByCompanyId_First(
			long companyId, OrderByComparator<BlogStatistics> orderByComparator)
		throws NoSuchBlogStatisticsException {

		return _collectionPersistenceFinderByCompanyId.findFirst(
			finderCache, new Object[] {companyId}, orderByComparator);
	}

	/**
	 * Returns the first blog statistics in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blog statistics, or <code>null</code> if a matching blog statistics could not be found
	 */
	@Override
	public BlogStatistics fetchByCompanyId_First(
		long companyId, OrderByComparator<BlogStatistics> orderByComparator) {

		return _collectionPersistenceFinderByCompanyId.fetchFirst(
			finderCache, new Object[] {companyId}, orderByComparator);
	}

	/**
	 * Removes all the blog statisticses where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		_collectionPersistenceFinderByCompanyId.remove(
			finderCache, new Object[] {companyId});
	}

	/**
	 * Returns the number of blog statisticses where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching blog statisticses
	 */
	@Override
	public int countByCompanyId(long companyId) {
		return _collectionPersistenceFinderByCompanyId.count(
			finderCache, new Object[] {companyId});
	}

	private CollectionPersistenceFinder
		<BlogStatistics, NoSuchBlogStatisticsException>
			_collectionPersistenceFinderByViewCountGreater;

	/**
	 * Returns all the blog statisticses where viewCount &gt; &#63;.
	 *
	 * @param viewCount the view count
	 * @return the matching blog statisticses
	 */
	@Override
	public List<BlogStatistics> findByViewCountGreater(long viewCount) {
		return findByViewCountGreater(
			viewCount, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blog statisticses where viewCount &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlogStatisticsModelImpl</code>.
	 * </p>
	 *
	 * @param viewCount the view count
	 * @param start the lower bound of the range of blog statisticses
	 * @param end the upper bound of the range of blog statisticses (not inclusive)
	 * @return the range of matching blog statisticses
	 */
	@Override
	public List<BlogStatistics> findByViewCountGreater(
		long viewCount, int start, int end) {

		return findByViewCountGreater(viewCount, start, end, null);
	}

	/**
	 * Returns an ordered range of all the blog statisticses where viewCount &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlogStatisticsModelImpl</code>.
	 * </p>
	 *
	 * @param viewCount the view count
	 * @param start the lower bound of the range of blog statisticses
	 * @param end the upper bound of the range of blog statisticses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blog statisticses
	 */
	@Override
	public List<BlogStatistics> findByViewCountGreater(
		long viewCount, int start, int end,
		OrderByComparator<BlogStatistics> orderByComparator) {

		return findByViewCountGreater(
			viewCount, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the blog statisticses where viewCount &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlogStatisticsModelImpl</code>.
	 * </p>
	 *
	 * @param viewCount the view count
	 * @param start the lower bound of the range of blog statisticses
	 * @param end the upper bound of the range of blog statisticses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching blog statisticses
	 */
	@Override
	public List<BlogStatistics> findByViewCountGreater(
		long viewCount, int start, int end,
		OrderByComparator<BlogStatistics> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByViewCountGreater.find(
			finderCache, new Object[] {viewCount}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first blog statistics in the ordered set where viewCount &gt; &#63;.
	 *
	 * @param viewCount the view count
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blog statistics
	 * @throws NoSuchBlogStatisticsException if a matching blog statistics could not be found
	 */
	@Override
	public BlogStatistics findByViewCountGreater_First(
			long viewCount, OrderByComparator<BlogStatistics> orderByComparator)
		throws NoSuchBlogStatisticsException {

		return _collectionPersistenceFinderByViewCountGreater.findFirst(
			finderCache, new Object[] {viewCount}, orderByComparator);
	}

	/**
	 * Returns the first blog statistics in the ordered set where viewCount &gt; &#63;.
	 *
	 * @param viewCount the view count
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blog statistics, or <code>null</code> if a matching blog statistics could not be found
	 */
	@Override
	public BlogStatistics fetchByViewCountGreater_First(
		long viewCount, OrderByComparator<BlogStatistics> orderByComparator) {

		return _collectionPersistenceFinderByViewCountGreater.fetchFirst(
			finderCache, new Object[] {viewCount}, orderByComparator);
	}

	/**
	 * Removes all the blog statisticses where viewCount &gt; &#63; from the database.
	 *
	 * @param viewCount the view count
	 */
	@Override
	public void removeByViewCountGreater(long viewCount) {
		_collectionPersistenceFinderByViewCountGreater.remove(
			finderCache, new Object[] {viewCount});
	}

	/**
	 * Returns the number of blog statisticses where viewCount &gt; &#63;.
	 *
	 * @param viewCount the view count
	 * @return the number of matching blog statisticses
	 */
	@Override
	public int countByViewCountGreater(long viewCount) {
		return _collectionPersistenceFinderByViewCountGreater.count(
			finderCache, new Object[] {viewCount});
	}

	public BlogStatisticsPersistenceImpl() {
		setModelClass(BlogStatistics.class);

		setModelImplClass(BlogStatisticsImpl.class);
		setModelPKClass(long.class);

		setTable(BlogStatisticsTable.INSTANCE);
	}

	/**
	 * Creates a new blog statistics with the primary key. Does not add the blog statistics to the database.
	 *
	 * @param blogStatisticsId the primary key for the new blog statistics
	 * @return the new blog statistics
	 */
	@Override
	public BlogStatistics create(long blogStatisticsId) {
		BlogStatistics blogStatistics = new BlogStatisticsImpl();

		blogStatistics.setNew(true);
		blogStatistics.setPrimaryKey(blogStatisticsId);

		blogStatistics.setCompanyId(CompanyThreadLocal.getCompanyId());

		return blogStatistics;
	}

	/**
	 * Removes the blog statistics with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param blogStatisticsId the primary key of the blog statistics
	 * @return the blog statistics that was removed
	 * @throws NoSuchBlogStatisticsException if a blog statistics with the primary key could not be found
	 */
	@Override
	public BlogStatistics remove(long blogStatisticsId)
		throws NoSuchBlogStatisticsException {

		return remove((Serializable)blogStatisticsId);
	}

	@Override
	protected BlogStatistics removeImpl(BlogStatistics blogStatistics) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(blogStatistics)) {
				blogStatistics = (BlogStatistics)session.get(
					BlogStatisticsImpl.class,
					blogStatistics.getPrimaryKeyObj());
			}

			if (blogStatistics != null) {
				session.delete(blogStatistics);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (blogStatistics != null) {
			clearCache(blogStatistics);
		}

		return blogStatistics;
	}

	@Override
	public BlogStatistics updateImpl(BlogStatistics blogStatistics) {
		boolean isNew = blogStatistics.isNew();

		if (!(blogStatistics instanceof BlogStatisticsModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(blogStatistics.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					blogStatistics);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in blogStatistics proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom BlogStatistics implementation " +
					blogStatistics.getClass());
		}

		BlogStatisticsModelImpl blogStatisticsModelImpl =
			(BlogStatisticsModelImpl)blogStatistics;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (blogStatistics.getCreateDate() == null)) {
			if (serviceContext == null) {
				blogStatistics.setCreateDate(date);
			}
			else {
				blogStatistics.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!blogStatisticsModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				blogStatistics.setModifiedDate(date);
			}
			else {
				blogStatistics.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(blogStatistics);
			}
			else {
				blogStatistics = (BlogStatistics)session.merge(blogStatistics);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		cacheUniqueFindersResult(blogStatistics, false);

		if (isNew) {
			blogStatistics.setNew(false);
		}

		blogStatistics.resetOriginalValues();

		return blogStatistics;
	}

	/**
	 * Returns the blog statistics with the primary key or throws a <code>NoSuchBlogStatisticsException</code> if it could not be found.
	 *
	 * @param blogStatisticsId the primary key of the blog statistics
	 * @return the blog statistics
	 * @throws NoSuchBlogStatisticsException if a blog statistics with the primary key could not be found
	 */
	@Override
	public BlogStatistics findByPrimaryKey(long blogStatisticsId)
		throws NoSuchBlogStatisticsException {

		return findByPrimaryKey((Serializable)blogStatisticsId);
	}

	/**
	 * Returns the blog statistics with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param blogStatisticsId the primary key of the blog statistics
	 * @return the blog statistics, or <code>null</code> if a blog statistics with the primary key could not be found
	 */
	@Override
	public BlogStatistics fetchByPrimaryKey(long blogStatisticsId) {
		return fetchByPrimaryKey((Serializable)blogStatisticsId);
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "blogStatisticsId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_BLOGSTATISTICS;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return BlogStatisticsModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the blog statistics persistence.
	 */
	@Activate
	public void activate() {
		_uniquePersistenceFinderByEntryId = new UniquePersistenceFinder<>(
			this,
			createUniqueFinderPath(
				FINDER_CLASS_NAME_ENTITY, "fetchByEntryId",
				new String[] {Long.class.getName()},
				new String[] {"blogEntryId"}, 0, 0, false,
				BlogStatistics::getBlogEntryId),
			_SQL_SELECT_BLOGSTATISTICS_WHERE, "",
			new FinderColumn<>(
				"blogStatistics.", "blogEntryId", FinderColumn.Type.LONG, "=",
				true, true, BlogStatistics::getBlogEntryId));

		_uniquePersistenceFinderByCompanyIdBlogEntryId =
			new UniquePersistenceFinder<>(
				this,
				createUniqueFinderPath(
					FINDER_CLASS_NAME_ENTITY, "fetchByCompanyIdBlogEntryId",
					new String[] {Long.class.getName(), Long.class.getName()},
					new String[] {"companyId", "blogEntryId"}, 0, 0, false,
					BlogStatistics::getCompanyId,
					BlogStatistics::getBlogEntryId),
				_SQL_SELECT_BLOGSTATISTICS_WHERE, "",
				new FinderColumn<>(
					"blogStatistics.", "companyId", FinderColumn.Type.LONG, "=",
					true, true, BlogStatistics::getCompanyId),
				new FinderColumn<>(
					"blogStatistics.", "blogEntryId", FinderColumn.Type.LONG,
					"=", true, true, BlogStatistics::getBlogEntryId));

		_collectionPersistenceFinderByCompanyId =
			new CollectionPersistenceFinder<>(
				this,
				new FinderPath(
					FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
					new String[] {
						Long.class.getName(), Integer.class.getName(),
						Integer.class.getName(),
						OrderByComparator.class.getName()
					},
					new String[] {"companyId"}, true),
				new FinderPath(
					FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
					"findByCompanyId", new String[] {Long.class.getName()},
					new String[] {"companyId"}, true),
				new FinderPath(
					FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
					"countByCompanyId", new String[] {Long.class.getName()},
					new String[] {"companyId"}, false),
				_SQL_SELECT_BLOGSTATISTICS_WHERE,
				_SQL_COUNT_BLOGSTATISTICS_WHERE,
				BlogStatisticsModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				"",
				new FinderColumn<>(
					"blogStatistics.", "companyId", FinderColumn.Type.LONG, "=",
					true, true, BlogStatistics::getCompanyId));

		_collectionPersistenceFinderByViewCountGreater =
			new CollectionPersistenceFinder<>(
				this,
				new FinderPath(
					FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
					"findByViewCountGreater",
					new String[] {
						Long.class.getName(), Integer.class.getName(),
						Integer.class.getName(),
						OrderByComparator.class.getName()
					},
					new String[] {"viewCount"}, true),
				null,
				new FinderPath(
					FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
					"countByViewCountGreater",
					new String[] {Long.class.getName()},
					new String[] {"viewCount"}, false),
				_SQL_SELECT_BLOGSTATISTICS_WHERE,
				_SQL_COUNT_BLOGSTATISTICS_WHERE,
				BlogStatisticsModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				"",
				new FinderColumn<>(
					"blogStatistics.", "viewCount", FinderColumn.Type.LONG, ">",
					true, true, BlogStatistics::getViewCount));

		BlogStatisticsUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		BlogStatisticsUtil.setPersistence(null);

		entityCache.removeCache(BlogStatisticsImpl.class.getName());
	}

	@Override
	@Reference(
		target = BlogStatsPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = BlogStatsPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = BlogStatsPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _ENTITY_ALIAS_PREFIX =
		BlogStatisticsModelImpl.ENTITY_ALIAS + ".";

	private static final String _SQL_SELECT_BLOGSTATISTICS =
		"SELECT blogStatistics FROM BlogStatistics blogStatistics";

	private static final String _SQL_SELECT_BLOGSTATISTICS_WHERE =
		"SELECT blogStatistics FROM BlogStatistics blogStatistics WHERE ";

	private static final String _SQL_COUNT_BLOGSTATISTICS_WHERE =
		"SELECT COUNT(blogStatistics) FROM BlogStatistics blogStatistics WHERE ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No BlogStatistics exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		BlogStatisticsPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-1421021033