/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.blogstatistics.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;BlogStats_BlogStatistics&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see BlogStatistics
 * @generated
 */
public class BlogStatisticsTable extends BaseTable<BlogStatisticsTable> {

	public static final BlogStatisticsTable INSTANCE =
		new BlogStatisticsTable();

	public final Column<BlogStatisticsTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<BlogStatisticsTable, Long> blogStatisticsId =
		createColumn(
			"blogStatisticsId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<BlogStatisticsTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<BlogStatisticsTable, Long> blogEntryId = createColumn(
		"blogEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<BlogStatisticsTable, Long> viewCount = createColumn(
		"viewCount", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<BlogStatisticsTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<BlogStatisticsTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private BlogStatisticsTable() {
		super("BlogStats_BlogStatistics", BlogStatisticsTable::new);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-1716832365