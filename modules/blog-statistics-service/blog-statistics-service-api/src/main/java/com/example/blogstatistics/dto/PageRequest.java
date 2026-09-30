package com.example.blogstatistics.dto;

import com.example.blogstatistics.exception.InvalidSortException;

import java.util.Optional;

public record PageRequest(
	int page, int pageSize, SortAttribute sortAttribute,
	SortDirection sortDirection) {

	public PageRequest {
		if (Optional.ofNullable(sortAttribute).isPresent() !=
			Optional.ofNullable(sortDirection).isPresent()) {

			throw new InvalidSortException(
				"Both sortAttribute and sortDirection are required");
		}
	}
}
