package com.example.blogstatistics.dto;


import com.example.blogstatistics.model.BlogStatistics;
import java.util.List;

public class Page {

    private final List<BlogStatistics> _items;
    private final int _page;
    private final int _pageSize;
    private final long _totalCount;

    public Page(List<BlogStatistics> items, int page, int pageSize, long totalCount) {
        this._items = items;
        this._page = page;
        this._pageSize = pageSize;
        this._totalCount = totalCount;
    }

    public List<BlogStatistics> getItems() { return _items; }
    public int getPage() { return _page; }
    public int getPageSize() { return _pageSize; }
    public long getTotalCount() { return _totalCount; }

    public int getTotalPages() {
        if (_pageSize <= 0) return 0;
        return (int) Math.ceil((double) _totalCount / _pageSize);
    }
}