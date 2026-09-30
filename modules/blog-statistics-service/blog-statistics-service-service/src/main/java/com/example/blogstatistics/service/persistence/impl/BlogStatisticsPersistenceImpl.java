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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	extends BasePersistenceImpl<BlogStatistics>
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

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByEntryId;

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

		BlogStatistics blogStatistics = fetchByEntryId(blogEntryId);

		if (blogStatistics == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("blogEntryId=");
			sb.append(blogEntryId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchBlogStatisticsException(sb.toString());
		}

		return blogStatistics;
	}

	/**
	 * Returns the blog statistics where blogEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param blogEntryId the blog entry ID
	 * @return the matching blog statistics, or <code>null</code> if a matching blog statistics could not be found
	 */
	@Override
	public BlogStatistics fetchByEntryId(long blogEntryId) {
		return fetchByEntryId(blogEntryId, true);
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

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {blogEntryId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByEntryId, finderArgs, this);
		}

		if (result instanceof BlogStatistics) {
			BlogStatistics blogStatistics = (BlogStatistics)result;

			if (blogEntryId != blogStatistics.getBlogEntryId()) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_BLOGSTATISTICS_WHERE);

			sb.append(_FINDER_COLUMN_ENTRYID_BLOGENTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(blogEntryId);

				List<BlogStatistics> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByEntryId, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {blogEntryId};
							}

							_log.warn(
								"BlogStatisticsPersistenceImpl.fetchByEntryId(long, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					BlogStatistics blogStatistics = list.get(0);

					result = blogStatistics;

					cacheResult(blogStatistics);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (BlogStatistics)result;
		}
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
		BlogStatistics blogStatistics = fetchByEntryId(blogEntryId);

		if (blogStatistics == null) {
			return 0;
		}

		return 1;
	}

	private static final String _FINDER_COLUMN_ENTRYID_BLOGENTRYID_2 =
		"blogStatistics.blogEntryId = ?";

	private FinderPath _finderPathFetchByCompanyIdBlogEntryId;

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

		BlogStatistics blogStatistics = fetchByCompanyIdBlogEntryId(
			companyId, blogEntryId);

		if (blogStatistics == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", blogEntryId=");
			sb.append(blogEntryId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchBlogStatisticsException(sb.toString());
		}

		return blogStatistics;
	}

	/**
	 * Returns the blog statistics where companyId = &#63; and blogEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param blogEntryId the blog entry ID
	 * @return the matching blog statistics, or <code>null</code> if a matching blog statistics could not be found
	 */
	@Override
	public BlogStatistics fetchByCompanyIdBlogEntryId(
		long companyId, long blogEntryId) {

		return fetchByCompanyIdBlogEntryId(companyId, blogEntryId, true);
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

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId, blogEntryId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByCompanyIdBlogEntryId, finderArgs, this);
		}

		if (result instanceof BlogStatistics) {
			BlogStatistics blogStatistics = (BlogStatistics)result;

			if ((companyId != blogStatistics.getCompanyId()) ||
				(blogEntryId != blogStatistics.getBlogEntryId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_BLOGSTATISTICS_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYIDBLOGENTRYID_COMPANYID_2);

			sb.append(_FINDER_COLUMN_COMPANYIDBLOGENTRYID_BLOGENTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(blogEntryId);

				List<BlogStatistics> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByCompanyIdBlogEntryId, finderArgs,
							list);
					}
				}
				else {
					BlogStatistics blogStatistics = list.get(0);

					result = blogStatistics;

					cacheResult(blogStatistics);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (BlogStatistics)result;
		}
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
		BlogStatistics blogStatistics = fetchByCompanyIdBlogEntryId(
			companyId, blogEntryId);

		if (blogStatistics == null) {
			return 0;
		}

		return 1;
	}

	private static final String
		_FINDER_COLUMN_COMPANYIDBLOGENTRYID_COMPANYID_2 =
			"blogStatistics.companyId = ? AND ";

	private static final String
		_FINDER_COLUMN_COMPANYIDBLOGENTRYID_BLOGENTRYID_2 =
			"blogStatistics.blogEntryId = ?";

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;

	/**
	 * Returns all the blog statisticses where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching blog statisticses
	 */
	@Override
	public List<BlogStatistics> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blog statisticses where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlogStatisticsModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of blog statisticses
	 * @param end the upper bound of the range of blog statisticses (not inclusive)
	 * @return the range of matching blog statisticses
	 */
	@Override
	public List<BlogStatistics> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

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
	 * @return the ordered range of matching blog statisticses
	 */
	@Override
	public List<BlogStatistics> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<BlogStatistics> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

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

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCompanyId;
				finderArgs = new Object[] {companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCompanyId;
			finderArgs = new Object[] {
				companyId, start, end, orderByComparator
			};
		}

		List<BlogStatistics> list = null;

		if (useFinderCache) {
			list = (List<BlogStatistics>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (BlogStatistics blogStatistics : list) {
					if (companyId != blogStatistics.getCompanyId()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_BLOGSTATISTICS_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(BlogStatisticsModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<BlogStatistics>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
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

		BlogStatistics blogStatistics = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (blogStatistics != null) {
			return blogStatistics;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchBlogStatisticsException(sb.toString());
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

		List<BlogStatistics> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last blog statistics in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blog statistics
	 * @throws NoSuchBlogStatisticsException if a matching blog statistics could not be found
	 */
	@Override
	public BlogStatistics findByCompanyId_Last(
			long companyId, OrderByComparator<BlogStatistics> orderByComparator)
		throws NoSuchBlogStatisticsException {

		BlogStatistics blogStatistics = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (blogStatistics != null) {
			return blogStatistics;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchBlogStatisticsException(sb.toString());
	}

	/**
	 * Returns the last blog statistics in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blog statistics, or <code>null</code> if a matching blog statistics could not be found
	 */
	@Override
	public BlogStatistics fetchByCompanyId_Last(
		long companyId, OrderByComparator<BlogStatistics> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<BlogStatistics> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the blog statisticses before and after the current blog statistics in the ordered set where companyId = &#63;.
	 *
	 * @param blogStatisticsId the primary key of the current blog statistics
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blog statistics
	 * @throws NoSuchBlogStatisticsException if a blog statistics with the primary key could not be found
	 */
	@Override
	public BlogStatistics[] findByCompanyId_PrevAndNext(
			long blogStatisticsId, long companyId,
			OrderByComparator<BlogStatistics> orderByComparator)
		throws NoSuchBlogStatisticsException {

		BlogStatistics blogStatistics = findByPrimaryKey(blogStatisticsId);

		Session session = null;

		try {
			session = openSession();

			BlogStatistics[] array = new BlogStatisticsImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, blogStatistics, companyId, orderByComparator, true);

			array[1] = blogStatistics;

			array[2] = getByCompanyId_PrevAndNext(
				session, blogStatistics, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogStatistics getByCompanyId_PrevAndNext(
		Session session, BlogStatistics blogStatistics, long companyId,
		OrderByComparator<BlogStatistics> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_BLOGSTATISTICS_WHERE);

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(BlogStatisticsModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						blogStatistics)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<BlogStatistics> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the blog statisticses where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (BlogStatistics blogStatistics :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(blogStatistics);
		}
	}

	/**
	 * Returns the number of blog statisticses where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching blog statisticses
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_BLOGSTATISTICS_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"blogStatistics.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByViewCountGreater;
	private FinderPath _finderPathWithPaginationCountByViewCountGreater;

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

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByViewCountGreater;
		finderArgs = new Object[] {viewCount, start, end, orderByComparator};

		List<BlogStatistics> list = null;

		if (useFinderCache) {
			list = (List<BlogStatistics>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (BlogStatistics blogStatistics : list) {
					if (viewCount >= blogStatistics.getViewCount()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_BLOGSTATISTICS_WHERE);

			sb.append(_FINDER_COLUMN_VIEWCOUNTGREATER_VIEWCOUNT_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(BlogStatisticsModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(viewCount);

				list = (List<BlogStatistics>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
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

		BlogStatistics blogStatistics = fetchByViewCountGreater_First(
			viewCount, orderByComparator);

		if (blogStatistics != null) {
			return blogStatistics;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("viewCount>");
		sb.append(viewCount);

		sb.append("}");

		throw new NoSuchBlogStatisticsException(sb.toString());
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

		List<BlogStatistics> list = findByViewCountGreater(
			viewCount, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last blog statistics in the ordered set where viewCount &gt; &#63;.
	 *
	 * @param viewCount the view count
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blog statistics
	 * @throws NoSuchBlogStatisticsException if a matching blog statistics could not be found
	 */
	@Override
	public BlogStatistics findByViewCountGreater_Last(
			long viewCount, OrderByComparator<BlogStatistics> orderByComparator)
		throws NoSuchBlogStatisticsException {

		BlogStatistics blogStatistics = fetchByViewCountGreater_Last(
			viewCount, orderByComparator);

		if (blogStatistics != null) {
			return blogStatistics;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("viewCount>");
		sb.append(viewCount);

		sb.append("}");

		throw new NoSuchBlogStatisticsException(sb.toString());
	}

	/**
	 * Returns the last blog statistics in the ordered set where viewCount &gt; &#63;.
	 *
	 * @param viewCount the view count
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blog statistics, or <code>null</code> if a matching blog statistics could not be found
	 */
	@Override
	public BlogStatistics fetchByViewCountGreater_Last(
		long viewCount, OrderByComparator<BlogStatistics> orderByComparator) {

		int count = countByViewCountGreater(viewCount);

		if (count == 0) {
			return null;
		}

		List<BlogStatistics> list = findByViewCountGreater(
			viewCount, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the blog statisticses before and after the current blog statistics in the ordered set where viewCount &gt; &#63;.
	 *
	 * @param blogStatisticsId the primary key of the current blog statistics
	 * @param viewCount the view count
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blog statistics
	 * @throws NoSuchBlogStatisticsException if a blog statistics with the primary key could not be found
	 */
	@Override
	public BlogStatistics[] findByViewCountGreater_PrevAndNext(
			long blogStatisticsId, long viewCount,
			OrderByComparator<BlogStatistics> orderByComparator)
		throws NoSuchBlogStatisticsException {

		BlogStatistics blogStatistics = findByPrimaryKey(blogStatisticsId);

		Session session = null;

		try {
			session = openSession();

			BlogStatistics[] array = new BlogStatisticsImpl[3];

			array[0] = getByViewCountGreater_PrevAndNext(
				session, blogStatistics, viewCount, orderByComparator, true);

			array[1] = blogStatistics;

			array[2] = getByViewCountGreater_PrevAndNext(
				session, blogStatistics, viewCount, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogStatistics getByViewCountGreater_PrevAndNext(
		Session session, BlogStatistics blogStatistics, long viewCount,
		OrderByComparator<BlogStatistics> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_BLOGSTATISTICS_WHERE);

		sb.append(_FINDER_COLUMN_VIEWCOUNTGREATER_VIEWCOUNT_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(BlogStatisticsModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(viewCount);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						blogStatistics)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<BlogStatistics> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the blog statisticses where viewCount &gt; &#63; from the database.
	 *
	 * @param viewCount the view count
	 */
	@Override
	public void removeByViewCountGreater(long viewCount) {
		for (BlogStatistics blogStatistics :
				findByViewCountGreater(
					viewCount, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(blogStatistics);
		}
	}

	/**
	 * Returns the number of blog statisticses where viewCount &gt; &#63;.
	 *
	 * @param viewCount the view count
	 * @return the number of matching blog statisticses
	 */
	@Override
	public int countByViewCountGreater(long viewCount) {
		FinderPath finderPath =
			_finderPathWithPaginationCountByViewCountGreater;

		Object[] finderArgs = new Object[] {viewCount};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_BLOGSTATISTICS_WHERE);

			sb.append(_FINDER_COLUMN_VIEWCOUNTGREATER_VIEWCOUNT_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(viewCount);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_VIEWCOUNTGREATER_VIEWCOUNT_2 =
		"blogStatistics.viewCount > ?";

	public BlogStatisticsPersistenceImpl() {
		setModelClass(BlogStatistics.class);

		setModelImplClass(BlogStatisticsImpl.class);
		setModelPKClass(long.class);

		setTable(BlogStatisticsTable.INSTANCE);
	}

	/**
	 * Caches the blog statistics in the entity cache if it is enabled.
	 *
	 * @param blogStatistics the blog statistics
	 */
	@Override
	public void cacheResult(BlogStatistics blogStatistics) {
		entityCache.putResult(
			BlogStatisticsImpl.class, blogStatistics.getPrimaryKey(),
			blogStatistics);

		finderCache.putResult(
			_finderPathFetchByEntryId,
			new Object[] {blogStatistics.getBlogEntryId()}, blogStatistics);

		finderCache.putResult(
			_finderPathFetchByCompanyIdBlogEntryId,
			new Object[] {
				blogStatistics.getCompanyId(), blogStatistics.getBlogEntryId()
			},
			blogStatistics);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the blog statisticses in the entity cache if it is enabled.
	 *
	 * @param blogStatisticses the blog statisticses
	 */
	@Override
	public void cacheResult(List<BlogStatistics> blogStatisticses) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (blogStatisticses.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (BlogStatistics blogStatistics : blogStatisticses) {
			if (entityCache.getResult(
					BlogStatisticsImpl.class, blogStatistics.getPrimaryKey()) ==
						null) {

				cacheResult(blogStatistics);
			}
		}
	}

	/**
	 * Clears the cache for all blog statisticses.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(BlogStatisticsImpl.class);

		finderCache.clearCache(BlogStatisticsImpl.class);
	}

	/**
	 * Clears the cache for the blog statistics.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(BlogStatistics blogStatistics) {
		entityCache.removeResult(BlogStatisticsImpl.class, blogStatistics);
	}

	@Override
	public void clearCache(List<BlogStatistics> blogStatisticses) {
		for (BlogStatistics blogStatistics : blogStatisticses) {
			entityCache.removeResult(BlogStatisticsImpl.class, blogStatistics);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(BlogStatisticsImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(BlogStatisticsImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		BlogStatisticsModelImpl blogStatisticsModelImpl) {

		Object[] args = new Object[] {blogStatisticsModelImpl.getBlogEntryId()};

		finderCache.putResult(
			_finderPathFetchByEntryId, args, blogStatisticsModelImpl);

		args = new Object[] {
			blogStatisticsModelImpl.getCompanyId(),
			blogStatisticsModelImpl.getBlogEntryId()
		};

		finderCache.putResult(
			_finderPathFetchByCompanyIdBlogEntryId, args,
			blogStatisticsModelImpl);
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

	/**
	 * Removes the blog statistics with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the blog statistics
	 * @return the blog statistics that was removed
	 * @throws NoSuchBlogStatisticsException if a blog statistics with the primary key could not be found
	 */
	@Override
	public BlogStatistics remove(Serializable primaryKey)
		throws NoSuchBlogStatisticsException {

		Session session = null;

		try {
			session = openSession();

			BlogStatistics blogStatistics = (BlogStatistics)session.get(
				BlogStatisticsImpl.class, primaryKey);

			if (blogStatistics == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchBlogStatisticsException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(blogStatistics);
		}
		catch (NoSuchBlogStatisticsException noSuchEntityException) {
			throw noSuchEntityException;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
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

		entityCache.putResult(
			BlogStatisticsImpl.class, blogStatisticsModelImpl, false, true);

		cacheUniqueFindersCache(blogStatisticsModelImpl);

		if (isNew) {
			blogStatistics.setNew(false);
		}

		blogStatistics.resetOriginalValues();

		return blogStatistics;
	}

	/**
	 * Returns the blog statistics with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the blog statistics
	 * @return the blog statistics
	 * @throws NoSuchBlogStatisticsException if a blog statistics with the primary key could not be found
	 */
	@Override
	public BlogStatistics findByPrimaryKey(Serializable primaryKey)
		throws NoSuchBlogStatisticsException {

		BlogStatistics blogStatistics = fetchByPrimaryKey(primaryKey);

		if (blogStatistics == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchBlogStatisticsException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

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

	/**
	 * Returns all the blog statisticses.
	 *
	 * @return the blog statisticses
	 */
	@Override
	public List<BlogStatistics> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blog statisticses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlogStatisticsModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of blog statisticses
	 * @param end the upper bound of the range of blog statisticses (not inclusive)
	 * @return the range of blog statisticses
	 */
	@Override
	public List<BlogStatistics> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the blog statisticses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlogStatisticsModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of blog statisticses
	 * @param end the upper bound of the range of blog statisticses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of blog statisticses
	 */
	@Override
	public List<BlogStatistics> findAll(
		int start, int end,
		OrderByComparator<BlogStatistics> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the blog statisticses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlogStatisticsModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of blog statisticses
	 * @param end the upper bound of the range of blog statisticses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of blog statisticses
	 */
	@Override
	public List<BlogStatistics> findAll(
		int start, int end, OrderByComparator<BlogStatistics> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<BlogStatistics> list = null;

		if (useFinderCache) {
			list = (List<BlogStatistics>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_BLOGSTATISTICS);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_BLOGSTATISTICS;

				sql = sql.concat(BlogStatisticsModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<BlogStatistics>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the blog statisticses from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (BlogStatistics blogStatistics : findAll()) {
			remove(blogStatistics);
		}
	}

	/**
	 * Returns the number of blog statisticses.
	 *
	 * @return the number of blog statisticses
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_BLOGSTATISTICS);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
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
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD));

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathFetchByEntryId = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByEntryId",
			new String[] {Long.class.getName()}, new String[] {"blogEntryId"},
			true);

		_finderPathFetchByCompanyIdBlogEntryId = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByCompanyIdBlogEntryId",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"companyId", "blogEntryId"}, true);

		_finderPathWithPaginationFindByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId"}, true);

		_finderPathWithoutPaginationFindByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			true);

		_finderPathCountByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			false);

		_finderPathWithPaginationFindByViewCountGreater = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByViewCountGreater",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"viewCount"}, true);

		_finderPathWithPaginationCountByViewCountGreater = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByViewCountGreater",
			new String[] {Long.class.getName()}, new String[] {"viewCount"},
			false);

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

	private static final String _SQL_SELECT_BLOGSTATISTICS =
		"SELECT blogStatistics FROM BlogStatistics blogStatistics";

	private static final String _SQL_SELECT_BLOGSTATISTICS_WHERE =
		"SELECT blogStatistics FROM BlogStatistics blogStatistics WHERE ";

	private static final String _SQL_COUNT_BLOGSTATISTICS =
		"SELECT COUNT(blogStatistics) FROM BlogStatistics blogStatistics";

	private static final String _SQL_COUNT_BLOGSTATISTICS_WHERE =
		"SELECT COUNT(blogStatistics) FROM BlogStatistics blogStatistics WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "blogStatistics.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No BlogStatistics exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No BlogStatistics exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		BlogStatisticsPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}