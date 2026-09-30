/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.blogstatistics.service.persistence.test;

import com.example.blogstatistics.exception.NoSuchBlogStatisticsException;
import com.example.blogstatistics.model.BlogStatistics;
import com.example.blogstatistics.service.BlogStatisticsLocalServiceUtil;
import com.example.blogstatistics.service.persistence.BlogStatisticsPersistence;
import com.example.blogstatistics.service.persistence.BlogStatisticsUtil;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class BlogStatisticsPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.example.blogstatistics.service"));

	@Before
	public void setUp() {
		_persistence = BlogStatisticsUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<BlogStatistics> iterator = _blogStatisticses.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		BlogStatistics blogStatistics = _persistence.create(pk);

		Assert.assertNotNull(blogStatistics);

		Assert.assertEquals(blogStatistics.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		BlogStatistics newBlogStatistics = addBlogStatistics();

		_persistence.remove(newBlogStatistics);

		BlogStatistics existingBlogStatistics = _persistence.fetchByPrimaryKey(
			newBlogStatistics.getPrimaryKey());

		Assert.assertNull(existingBlogStatistics);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addBlogStatistics();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		BlogStatistics newBlogStatistics = addBlogStatistics();

		newBlogStatistics.setCompanyId(RandomTestUtil.nextLong());

		newBlogStatistics.setBlogEntryId(RandomTestUtil.nextLong());

		newBlogStatistics.setViewCount(RandomTestUtil.nextLong());

		newBlogStatistics.setCreateDate(RandomTestUtil.nextDate());

		newBlogStatistics.setModifiedDate(RandomTestUtil.nextDate());

		newBlogStatistics = _persistence.update(newBlogStatistics);

		_blogStatisticses.add(newBlogStatistics);

		BlogStatistics existingBlogStatistics = _persistence.findByPrimaryKey(
			newBlogStatistics.getPrimaryKey());

		Assert.assertEquals(
			existingBlogStatistics.getMvccVersion(),
			newBlogStatistics.getMvccVersion());
		Assert.assertEquals(
			existingBlogStatistics.getBlogStatisticsId(),
			newBlogStatistics.getBlogStatisticsId());
		Assert.assertEquals(
			existingBlogStatistics.getCompanyId(),
			newBlogStatistics.getCompanyId());
		Assert.assertEquals(
			existingBlogStatistics.getBlogEntryId(),
			newBlogStatistics.getBlogEntryId());
		Assert.assertEquals(
			existingBlogStatistics.getViewCount(),
			newBlogStatistics.getViewCount());
		Assert.assertEquals(
			Time.getShortTimestamp(existingBlogStatistics.getCreateDate()),
			Time.getShortTimestamp(newBlogStatistics.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingBlogStatistics.getModifiedDate()),
			Time.getShortTimestamp(newBlogStatistics.getModifiedDate()));
	}

	@Test
	public void testCountByEntryId() throws Exception {
		_persistence.countByEntryId(RandomTestUtil.nextLong());

		_persistence.countByEntryId(0L);
	}

	@Test
	public void testCountByCompanyIdBlogEntryId() throws Exception {
		_persistence.countByCompanyIdBlogEntryId(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByCompanyIdBlogEntryId(0L, 0L);
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testCountByViewCountGreater() throws Exception {
		_persistence.countByViewCountGreater(RandomTestUtil.nextLong());

		_persistence.countByViewCountGreater(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		BlogStatistics newBlogStatistics = addBlogStatistics();

		BlogStatistics existingBlogStatistics = _persistence.findByPrimaryKey(
			newBlogStatistics.getPrimaryKey());

		Assert.assertEquals(existingBlogStatistics, newBlogStatistics);
	}

	@Test(expected = NoSuchBlogStatisticsException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<BlogStatistics> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"BlogStats_BlogStatistics", "mvccVersion", true, "blogStatisticsId",
			true, "companyId", true, "blogEntryId", true, "viewCount", true,
			"createDate", true, "modifiedDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		BlogStatistics newBlogStatistics = addBlogStatistics();

		BlogStatistics existingBlogStatistics = _persistence.fetchByPrimaryKey(
			newBlogStatistics.getPrimaryKey());

		Assert.assertEquals(existingBlogStatistics, newBlogStatistics);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		BlogStatistics missingBlogStatistics = _persistence.fetchByPrimaryKey(
			pk);

		Assert.assertNull(missingBlogStatistics);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		BlogStatistics newBlogStatistics1 = addBlogStatistics();
		BlogStatistics newBlogStatistics2 = addBlogStatistics();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newBlogStatistics1.getPrimaryKey());
		primaryKeys.add(newBlogStatistics2.getPrimaryKey());

		Map<Serializable, BlogStatistics> blogStatisticses =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, blogStatisticses.size());
		Assert.assertEquals(
			newBlogStatistics1,
			blogStatisticses.get(newBlogStatistics1.getPrimaryKey()));
		Assert.assertEquals(
			newBlogStatistics2,
			blogStatisticses.get(newBlogStatistics2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, BlogStatistics> blogStatisticses =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(blogStatisticses.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		BlogStatistics newBlogStatistics = addBlogStatistics();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newBlogStatistics.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, BlogStatistics> blogStatisticses =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, blogStatisticses.size());
		Assert.assertEquals(
			newBlogStatistics,
			blogStatisticses.get(newBlogStatistics.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, BlogStatistics> blogStatisticses =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(blogStatisticses.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		BlogStatistics newBlogStatistics = addBlogStatistics();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newBlogStatistics.getPrimaryKey());

		Map<Serializable, BlogStatistics> blogStatisticses =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, blogStatisticses.size());
		Assert.assertEquals(
			newBlogStatistics,
			blogStatisticses.get(newBlogStatistics.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			BlogStatisticsLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<BlogStatistics>() {

				@Override
				public void performAction(BlogStatistics blogStatistics) {
					Assert.assertNotNull(blogStatistics);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		BlogStatistics newBlogStatistics = addBlogStatistics();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			BlogStatistics.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"blogStatisticsId", newBlogStatistics.getBlogStatisticsId()));

		List<BlogStatistics> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		BlogStatistics existingBlogStatistics = result.get(0);

		Assert.assertEquals(existingBlogStatistics, newBlogStatistics);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			BlogStatistics.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"blogStatisticsId", RandomTestUtil.nextLong()));

		List<BlogStatistics> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		BlogStatistics newBlogStatistics = addBlogStatistics();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			BlogStatistics.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("blogStatisticsId"));

		Object newBlogStatisticsId = newBlogStatistics.getBlogStatisticsId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"blogStatisticsId", new Object[] {newBlogStatisticsId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingBlogStatisticsId = result.get(0);

		Assert.assertEquals(existingBlogStatisticsId, newBlogStatisticsId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			BlogStatistics.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("blogStatisticsId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"blogStatisticsId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		BlogStatistics newBlogStatistics = addBlogStatistics();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(newBlogStatistics.getPrimaryKey()));
	}

	@Test
	public void testResetOriginalValuesWithDynamicQueryLoadFromDatabase()
		throws Exception {

		_testResetOriginalValuesWithDynamicQuery(true);
	}

	@Test
	public void testResetOriginalValuesWithDynamicQueryLoadFromSession()
		throws Exception {

		_testResetOriginalValuesWithDynamicQuery(false);
	}

	private void _testResetOriginalValuesWithDynamicQuery(boolean clearSession)
		throws Exception {

		BlogStatistics newBlogStatistics = addBlogStatistics();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			BlogStatistics.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"blogStatisticsId", newBlogStatistics.getBlogStatisticsId()));

		List<BlogStatistics> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(BlogStatistics blogStatistics) {
		Assert.assertEquals(
			Long.valueOf(blogStatistics.getBlogEntryId()),
			ReflectionTestUtil.<Long>invoke(
				blogStatistics, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "blogEntryId"));

		Assert.assertEquals(
			Long.valueOf(blogStatistics.getCompanyId()),
			ReflectionTestUtil.<Long>invoke(
				blogStatistics, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "companyId"));
		Assert.assertEquals(
			Long.valueOf(blogStatistics.getBlogEntryId()),
			ReflectionTestUtil.<Long>invoke(
				blogStatistics, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "blogEntryId"));
	}

	protected BlogStatistics addBlogStatistics() throws Exception {
		long pk = RandomTestUtil.nextLong();

		BlogStatistics blogStatistics = _persistence.create(pk);

		blogStatistics.setCompanyId(RandomTestUtil.nextLong());

		blogStatistics.setBlogEntryId(RandomTestUtil.nextLong());

		blogStatistics.setViewCount(RandomTestUtil.nextLong());

		blogStatistics.setCreateDate(RandomTestUtil.nextDate());

		blogStatistics.setModifiedDate(RandomTestUtil.nextDate());

		_blogStatisticses.add(_persistence.update(blogStatistics));

		return blogStatistics;
	}

	private List<BlogStatistics> _blogStatisticses =
		new ArrayList<BlogStatistics>();
	private BlogStatisticsPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}
// LIFERAY-SERVICE-BUILDER-HASH:1769140466