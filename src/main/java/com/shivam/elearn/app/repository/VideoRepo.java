package com.shivam.elearn.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.shivam.elearn.app.entity.Course;
import com.shivam.elearn.app.entity.Video;


@EnableJpaRepositories
public interface VideoRepo extends JpaRepository<Video, String>{
	
	Optional<Video> findByTitle(String title);
	 
	List<Video> findByCourse(Course course);
	
//	List<Video> findByTitleContainingIgnoreCaseOrShortDescContainingIgnoreCase(String keyword);
	
//	List<Video> findByTitleContainingIgnoreCaseOrDescContainingIgnoreCase(String keyword);
	
    List<Video> findByTitleContainingIgnoreCaseOrDescContainingIgnoreCase(String keyword, String keyword1);


}
