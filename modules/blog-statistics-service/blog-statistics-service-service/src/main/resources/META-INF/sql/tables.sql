create table BlogStats_BlogStatistics (
	mvccVersion LONG default 0 not null,
	blogStatisticsId LONG not null primary key,
	companyId LONG,
	blogEntryId LONG,
	viewCount LONG,
	createDate DATE null,
	modifiedDate DATE null
);