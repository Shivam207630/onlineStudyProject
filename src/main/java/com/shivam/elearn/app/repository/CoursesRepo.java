package com.shivam.elearn.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shivam.elearn.app.entity.Course;

public interface CoursesRepo extends JpaRepository<Course, String> {

	Optional<Course> findByTitle(String title);

	List<Course> findByLive(boolean live);

	List<Course> findByTitleContainingIgnoreCaseOrShortDescContainingIgnoreCase(String keyword, String keyword1);

}
