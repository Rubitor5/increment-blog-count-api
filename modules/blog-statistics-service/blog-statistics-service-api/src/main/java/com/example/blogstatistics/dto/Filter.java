package com.example.blogstatistics.dto;

public class Filter {

    private final Long _companyId;
    private final Long _blogEntryId;
    private final Long _minViews;
    private final Long _maxViews;

    public Filter(Long companyId, Long blogEntryId, Long minViews, Long maxViews) {
        this._companyId = companyId;
        this._blogEntryId = blogEntryId;
        this._minViews = minViews;
        this._maxViews = maxViews;
    }

    public Long getCompanyId() { return _companyId; }
    public Long getBlogEntryId() { return _blogEntryId; }
    public Long getMinViews() { return _minViews; }
    public Long getMaxViews() { return _maxViews; }
}
