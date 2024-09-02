package com.shivam.elearn.app.serviceImpl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shivam.elearn.app.config.AppConstant;
import com.shivam.elearn.app.dto.CoursesDto;
import com.shivam.elearn.app.entity.Course;
import com.shivam.elearn.app.exception.ResourceNotFoundException;
import com.shivam.elearn.app.repository.CoursesRepo;
import com.shivam.elearn.app.services.CourseServices;
import com.shivam.elearn.app.services.FileService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CourseServiceImpl implements CourseServices {
	@Autowired
	private CoursesRepo coursesRepo;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private FileService fileService;

	@Override
	public CoursesDto create(CoursesDto coursesDto) {
		coursesDto.setCourseid(UUID.randomUUID().toString());
		coursesDto.setAddeDate(new Date());
		Course saveCourse = coursesRepo.save(this.dtotoEntity(coursesDto));
		return entityToDto(saveCourse);
	}

	@Override
	public Page<CoursesDto> getAll(Pageable pageable) {
		Page<Course> findAll = coursesRepo.findAll(pageable);

		List<CoursesDto> list = findAll.stream().map(courses -> entityToDto(courses)).collect(Collectors.toList());
		return new PageImpl<>(list, pageable, findAll.getTotalElements());
	}

	@Override
	public CoursesDto update(CoursesDto coursesDto, String id) {
		log.info("coursesDto>>>>>{}", coursesDto);
		Course course = coursesRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Data Not found"));
		course.setTitle(coursesDto.getTitle());
		course.setShortDesc(coursesDto.getShortDesc());
		course.setLongDesc(coursesDto.getLongDesc());
		course.setPrice(coursesDto.getPrice());
		course.setLive(coursesDto.isLive());
		course.setDiscount(coursesDto.getDiscount());
		Course saveCourse = coursesRepo.save(course);

		log.info("saveCourse>>>>>>{}", saveCourse);

		return modelMapper.map(course, CoursesDto.class);
	}

	@Override
	public void delete(String id) {
		Course course = coursesRepo.findById(id)
				.orElseThrow((() -> new ResourceNotFoundException("Courses not found")));
		coursesRepo.delete(course);

	}

	@Override
	public CoursesDto findSingle(String id) {
		Course course = coursesRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Data Not found"));
		return modelMapper.map(course, CoursesDto.class);
	}

	@Override
	public List<CoursesDto> searchByKeywords(String keyword) {
		List<Course> findByTitleContainingIgnoreCaseOrShortDescContainingIgnoreCase = coursesRepo
				.findByTitleContainingIgnoreCaseOrShortDescContainingIgnoreCase(keyword, keyword);
		log.info("findByTitleContainingIgnoreCaseOrShortDescContainingIgnoreCase>>>>{}",
				findByTitleContainingIgnoreCaseOrShortDescContainingIgnoreCase);
		List<CoursesDto> list = findByTitleContainingIgnoreCaseOrShortDescContainingIgnoreCase.stream()
				.map(cou -> modelMapper.map(cou, CoursesDto.class)).collect(Collectors.toList());
		log.info("list>>>>{}", list);
		return list;
	}

	@Override
	public CoursesDto saveBanner(MultipartFile file, String courseid) throws IOException {
		Course course = coursesRepo.findById(courseid).orElseThrow(() -> new ResourceNotFoundException("Course not found!!"));
		String filePath = fileService.save(file, AppConstant.COURSE_BANNER_DIR, file.getOriginalFilename());
		course.setBanner(filePath);
		course.setBannerContentType(file.getContentType());
		return modelMapper.map(coursesRepo.save(course), CoursesDto.class);
	}

	public CoursesDto entityToDto(Course course) {
		CoursesDto coursesDto = modelMapper.map(course, CoursesDto.class);

		return coursesDto;

	}

	public Course dtotoEntity(CoursesDto coursesDto) {
		Course course = modelMapper.map(coursesDto, Course.class);

		return course;

	}

}
