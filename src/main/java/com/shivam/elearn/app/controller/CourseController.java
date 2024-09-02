package com.shivam.elearn.app.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shivam.elearn.app.dto.CoursesDto;
import com.shivam.elearn.app.dto.CustomMessage;
import com.shivam.elearn.app.services.CourseServices;
import com.shivam.elearn.app.services.FileService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {
	@Autowired
	private CourseServices courseServices;
	@Autowired
	private FileService fileService;

	@PostMapping
	public ResponseEntity<CoursesDto> create(@RequestBody CoursesDto coursesDto) {
		CoursesDto createCoursesDto = courseServices.create(coursesDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(createCoursesDto);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CoursesDto> update(@PathVariable String id, @RequestBody CoursesDto coursesDto) {
		CoursesDto updateCoursesDto = courseServices.update(coursesDto, id);
		log.info("courseDto>>>>>> {}", coursesDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(updateCoursesDto);

	}

	@DeleteMapping("/{courseid}")
	public ResponseEntity<CustomMessage> delte(@PathVariable String courseid) {
		courseServices.delete(courseid);
		CustomMessage customMessage = new CustomMessage();
		customMessage.setMessage("Delete is sucessfully");
		customMessage.setSucess(true);
		return ResponseEntity.status(HttpStatus.OK).body(customMessage);
	}

	@GetMapping("/{courseid}")
	public ResponseEntity<CoursesDto> getSingle(@PathVariable String courseid) {
		CoursesDto findSingle = courseServices.findSingle(courseid);
		return ResponseEntity.status(HttpStatus.OK).body(findSingle);
	}

	@GetMapping
	public ResponseEntity<Page<CoursesDto>> getAll(Pageable pageable) {
		Page<CoursesDto> all = courseServices.getAll(pageable);
		return ResponseEntity.status(HttpStatus.OK).body(all);
	}

	@GetMapping("/search")
	public ResponseEntity<List<CoursesDto>> searchCourses(@RequestParam String keyeord) {
		List<CoursesDto> searchByKeywords = courseServices.searchByKeywords(keyeord);
		log.info("searchByKeywords", searchByKeywords);
		return ResponseEntity.ok(searchByKeywords);
	}


	
	   @PostMapping("/{courseId}/banners")
	    public ResponseEntity<?> uploadBanner(
	            @PathVariable String courseId,
	            @RequestParam("banner") MultipartFile banner
	    ) throws IOException {

	        // validate the file

	        String contentType = banner.getContentType();

	        if (contentType == null) {
	            contentType = "image/png";
	        } else if (contentType.equalsIgnoreCase("image/png") || contentType.equalsIgnoreCase("image/jpeg"))
	        {
	        } else {
	            CustomMessage customMessage = new CustomMessage();
	            customMessage.setSucess(false);
	            customMessage.setMessage("Invalid file");

	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customMessage);

	        }

	        //
	        System.out.println(banner.getOriginalFilename());
	        System.out.println(banner.getName());
	        System.out.println(banner.getSize());
	        System.out.println(banner.getContentType());
	        CoursesDto courseDto = courseServices.saveBanner(banner, courseId);
//	        System.out.println(banner.get);
	        return ResponseEntity.ok(courseDto);
	    }

}
