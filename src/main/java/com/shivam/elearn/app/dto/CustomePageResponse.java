package com.shivam.elearn.app.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomePageResponse<T> {
    private int pageNumber;
	private int pageSize;
    private int totalPages;
	private long totalElement;

	private boolean isLast;

	private List<T> content;

}
