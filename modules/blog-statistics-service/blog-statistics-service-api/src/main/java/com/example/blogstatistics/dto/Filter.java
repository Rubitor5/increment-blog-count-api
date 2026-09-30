package com.example.blogstatistics.dto;

public record Filter(
        Long companyId,
        Long blogEntryId,
        Long minViews,
        Long maxViews
) {}