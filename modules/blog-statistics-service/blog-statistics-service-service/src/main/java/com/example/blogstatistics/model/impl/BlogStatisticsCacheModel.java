/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.blogstatistics.model.impl;

import com.example.blogstatistics.model.BlogStatistics;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing BlogStatistics in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class BlogStatisticsCacheModel
	implements CacheModel<BlogStatistics>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof BlogStatisticsCacheModel)) {
			return false;
		}

		BlogStatisticsCacheModel blogStatisticsCacheModel =
			(BlogStatisticsCacheModel)object;

		if ((blogStatisticsId == blogStatisticsCacheModel.blogStatisticsId) &&
			(mvccVersion == blogStatisticsCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, blogStatisticsId);

		return HashUtil.hash(hashCode, mvccVersion);
	}

	@Override
	public long getMvccVersion() {
		return mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		this.mvccVersion = mvccVersion;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", blogStatisticsId=");
		sb.append(blogStatisticsId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", blogEntryId=");
		sb.append(blogEntryId);
		sb.append(", viewCount=");
		sb.append(viewCount);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public BlogStatistics toEntityModel() {
		BlogStatisticsImpl blogStatisticsImpl = new BlogStatisticsImpl();

		blogStatisticsImpl.setMvccVersion(mvccVersion);
		blogStatisticsImpl.setBlogStatisticsId(blogStatisticsId);
		blogStatisticsImpl.setCompanyId(companyId);
		blogStatisticsImpl.setBlogEntryId(blogEntryId);
		blogStatisticsImpl.setViewCount(viewCount);

		if (createDate == Long.MIN_VALUE) {
			blogStatisticsImpl.setCreateDate(null);
		}
		else {
			blogStatisticsImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			blogStatisticsImpl.setModifiedDate(null);
		}
		else {
			blogStatisticsImpl.setModifiedDate(new Date(modifiedDate));
		}

		blogStatisticsImpl.resetOriginalValues();

		return blogStatisticsImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		blogStatisticsId = objectInput.readLong();

		companyId = objectInput.readLong();

		blogEntryId = objectInput.readLong();

		viewCount = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(blogStatisticsId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(blogEntryId);

		objectOutput.writeLong(viewCount);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);
	}

	public long mvccVersion;
	public long blogStatisticsId;
	public long companyId;
	public long blogEntryId;
	public long viewCount;
	public long createDate;
	public long modifiedDate;

}
// LIFERAY-SERVICE-BUILDER-HASH:483396254