package com.shivam.elearn.app.services;

import java.util.List;

import com.shivam.elearn.app.dto.CategoryDto;
import com.shivam.elearn.app.dto.CoursesDto;
import com.shivam.elearn.app.dto.CustomePageResponse;

public interface CategoryServices {
	
	CategoryDto create(CategoryDto categoryDto);
	
	CustomePageResponse<CategoryDto> getAllCategoryDtos(int pageNumber,int pageSize,String sortBy);
	
	CategoryDto getSingle(String id);
	
	void delete(String id);
	
	CategoryDto updateCategoryDto(CategoryDto categoryDto,String id);
	
	public void addCoursesToCategory(String catId,String id);
	
	List<CoursesDto> getCoursesOfCat(String id);

}
