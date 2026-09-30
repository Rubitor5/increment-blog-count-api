package com.example.blog.statistics.rest.dto;

import com.example.blogstatistics.dto.SortAttribute;
import com.example.blogstatistics.dto.SortDirection;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

public class GetAllViewCountsQuery {

	@QueryParam("companyId")
	private Long _companyId;

	@QueryParam("blogEntryId")
	private Long _blogEntryId;

	@QueryParam("minViews")
	private Long _minViews;

	@QueryParam("maxViews")
	private Long _maxViews;

	@QueryParam("page")
	@DefaultValue("1")
	private int _page = 1;

	@QueryParam("pageSize")
	@DefaultValue("20")
	private int _pageSize = 20;

	@QueryParam("sortAttribute")
	private SortAttribute _sortAttribute;

	@QueryParam("sortDirection")
	private SortDirection _sortDirection;

	public Long getCompanyId() {
		return _companyId;
	}

	public Long getBlogEntryId() {
		return _blogEntryId;
	}

	public Long getMinViews() {
		return _minViews;
	}

	public Long getMaxViews() {
		return _maxViews;
	}

	public int getPage() {
		return _page;
	}

	public int getPageSize() {
		return _pageSize;
	}

	public SortAttribute getSortAttribute() {
		return _sortAttribute;
	}

	public SortDirection getSortDirection() {
		return _sortDirection;
	}

	public void setCompanyId(Long companyId) {
		_companyId = companyId;
	}

	public void setBlogEntryId(Long blogEntryId) {
		_blogEntryId = blogEntryId;
	}

	public void setMinViews(Long minViews) {
		_minViews = minViews;
	}

	public void setMaxViews(Long maxViews) {
		_maxViews = maxViews;
	}

	public void setPage(int page) {
		_page = page;
	}

	public void setPageSize(int pageSize) {
		_pageSize = pageSize;
	}

	public void setSortAttribute(SortAttribute sortAttribute) {
		_sortAttribute = sortAttribute;
	}

	public void setSortDirection(SortDirection sortDirection) {
		_sortDirection = sortDirection;
	}
}
