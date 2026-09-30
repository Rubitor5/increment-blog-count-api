create index IX_BCB0AAE on BlogStats_BlogStatistics (blogEntryId);
create unique index IX_FCCE9180 on BlogStats_BlogStatistics (companyId, blogEntryId);
create index IX_24E2E16D on BlogStats_BlogStatistics (viewCount);