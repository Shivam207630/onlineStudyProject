package com.shivam.elearn.app.services;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.shivam.elearn.app.dto.CoursesDto;

public interface CourseServices {

	CoursesDto create(CoursesDto coursesDto);

	Page<CoursesDto> getAll(Pageable pageable);

	CoursesDto update(CoursesDto coursesDto, String id);

	void delete(String id);

	CoursesDto findSingle(String id);

	List<CoursesDto> searchByKeywords(String keyword);

	public CoursesDto saveBanner(MultipartFile file, String courseid) throws IOException;

}
