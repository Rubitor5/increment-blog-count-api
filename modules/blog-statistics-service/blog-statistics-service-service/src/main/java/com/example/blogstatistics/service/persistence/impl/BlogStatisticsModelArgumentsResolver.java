/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.blogstatistics.service.persistence.impl;

import com.example.blogstatistics.model.BlogStatisticsTable;
import com.example.blogstatistics.model.impl.BlogStatisticsImpl;
import com.example.blogstatistics.model.impl.BlogStatisticsModelImpl;

import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.model.BaseModel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;

/**
 * The arguments resolver class for retrieving value from BlogStatistics.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(
	property = {
		"class.name=com.example.blogstatistics.model.impl.BlogStatisticsImpl",
		"table.name=BlogStats_BlogStatistics"
	},
	service = ArgumentsResolver.class
)
public class BlogStatisticsModelArgumentsResolver implements ArgumentsResolver {

	@Override
	public Object[] getArguments(
		FinderPath finderPath, BaseModel<?> baseModel, boolean checkColumn,
		boolean original) {

		String[] columnNames = finderPath.getColumnNames();

		if ((columnNames == null) || (columnNames.length == 0)) {
			if (baseModel.isNew()) {
				return new Object[0];
			}

			return null;
		}

		BlogStatisticsModelImpl blogStatisticsModelImpl =
			(BlogStatisticsModelImpl)baseModel;

		long columnBitmask = blogStatisticsModelImpl.getColumnBitmask();

		if (!checkColumn || (columnBitmask == 0)) {
			return _getValue(blogStatisticsModelImpl, finderPath, original);
		}

		Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
			finderPath);

		if (finderPathColumnBitmask == null) {
			finderPathColumnBitmask = 0L;

			for (String columnName : columnNames) {
				finderPathColumnBitmask |=
					blogStatisticsModelImpl.getColumnBitmask(columnName);
			}

			_finderPathColumnBitmasksCache.put(
				finderPath, finderPathColumnBitmask);
		}

		if ((columnBitmask & finderPathColumnBitmask) != 0) {
			return _getValue(blogStatisticsModelImpl, finderPath, original);
		}

		return null;
	}

	@Override
	public String getClassName() {
		return BlogStatisticsImpl.class.getName();
	}

	@Override
	public String getTableName() {
		return BlogStatisticsTable.INSTANCE.getTableName();
	}

	private static Object[] _getValue(
		BlogStatisticsModelImpl blogStatisticsModelImpl, FinderPath finderPath,
		boolean original) {

		String[] columnNames = finderPath.getColumnNames();

		Object[] arguments = new Object[columnNames.length];

		for (int i = 0; i < arguments.length; i++) {
			String columnName = columnNames[i];

			Object value;

			if (original) {
				value = blogStatisticsModelImpl.getColumnOriginalValue(
					columnName);
			}
			else {
				value = blogStatisticsModelImpl.getColumnValue(columnName);
			}

			arguments[i] = finderPath.normalizeArgument(i, value);
		}

		return arguments;
	}

	private static final Map<FinderPath, Long> _finderPathColumnBitmasksCache =
		new ConcurrentHashMap<>();

}
// LIFERAY-SERVICE-BUILDER-HASH:1881995573