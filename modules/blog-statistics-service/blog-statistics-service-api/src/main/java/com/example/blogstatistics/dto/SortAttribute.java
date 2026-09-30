package com.example.blogstatistics.dto;

public enum SortAttribute {

	BLOG_STATISTICS_ID("blogStatisticsId"),
	COMPANY_ID("companyId"),
	BLOG_ENTRY_ID("blogEntryId"),
	VIEW_COUNT("viewCount"),
	CREATE_DATE("createDate"),
	MODIFIED_DATE("modifiedDate");

	private final String _fieldName;

	SortAttribute(String fieldName) {
		_fieldName = fieldName;
	}

	public String getFieldName() {
		return _fieldName;
	}
}
