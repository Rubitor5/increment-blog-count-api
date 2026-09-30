/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.blogstatistics.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link BlogStatistics}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BlogStatistics
 * @generated
 */
public class BlogStatisticsWrapper
	extends BaseModelWrapper<BlogStatistics>
	implements BlogStatistics, ModelWrapper<BlogStatistics> {

	public BlogStatisticsWrapper(BlogStatistics blogStatistics) {
		super(blogStatistics);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("blogStatisticsId", getBlogStatisticsId());
		attributes.put("companyId", getCompanyId());
		attributes.put("blogEntryId", getBlogEntryId());
		attributes.put("viewCount", getViewCount());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long blogStatisticsId = (Long)attributes.get("blogStatisticsId");

		if (blogStatisticsId != null) {
			setBlogStatisticsId(blogStatisticsId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long blogEntryId = (Long)attributes.get("blogEntryId");

		if (blogEntryId != null) {
			setBlogEntryId(blogEntryId);
		}

		Long viewCount = (Long)attributes.get("viewCount");

		if (viewCount != null) {
			setViewCount(viewCount);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}
	}

	@Override
	public BlogStatistics cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the blog entry ID of this blog statistics.
	 *
	 * @return the blog entry ID of this blog statistics
	 */
	@Override
	public long getBlogEntryId() {
		return model.getBlogEntryId();
	}

	/**
	 * Returns the blog statistics ID of this blog statistics.
	 *
	 * @return the blog statistics ID of this blog statistics
	 */
	@Override
	public long getBlogStatisticsId() {
		return model.getBlogStatisticsId();
	}

	/**
	 * Returns the company ID of this blog statistics.
	 *
	 * @return the company ID of this blog statistics
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this blog statistics.
	 *
	 * @return the create date of this blog statistics
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the modified date of this blog statistics.
	 *
	 * @return the modified date of this blog statistics
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this blog statistics.
	 *
	 * @return the mvcc version of this blog statistics
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this blog statistics.
	 *
	 * @return the primary key of this blog statistics
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the view count of this blog statistics.
	 *
	 * @return the view count of this blog statistics
	 */
	@Override
	public long getViewCount() {
		return model.getViewCount();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the blog entry ID of this blog statistics.
	 *
	 * @param blogEntryId the blog entry ID of this blog statistics
	 */
	@Override
	public void setBlogEntryId(long blogEntryId) {
		model.setBlogEntryId(blogEntryId);
	}

	/**
	 * Sets the blog statistics ID of this blog statistics.
	 *
	 * @param blogStatisticsId the blog statistics ID of this blog statistics
	 */
	@Override
	public void setBlogStatisticsId(long blogStatisticsId) {
		model.setBlogStatisticsId(blogStatisticsId);
	}

	/**
	 * Sets the company ID of this blog statistics.
	 *
	 * @param companyId the company ID of this blog statistics
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this blog statistics.
	 *
	 * @param createDate the create date of this blog statistics
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the modified date of this blog statistics.
	 *
	 * @param modifiedDate the modified date of this blog statistics
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this blog statistics.
	 *
	 * @param mvccVersion the mvcc version of this blog statistics
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this blog statistics.
	 *
	 * @param primaryKey the primary key of this blog statistics
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the view count of this blog statistics.
	 *
	 * @param viewCount the view count of this blog statistics
	 */
	@Override
	public void setViewCount(long viewCount) {
		model.setViewCount(viewCount);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected BlogStatisticsWrapper wrap(BlogStatistics blogStatistics) {
		return new BlogStatisticsWrapper(blogStatistics);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-403218662