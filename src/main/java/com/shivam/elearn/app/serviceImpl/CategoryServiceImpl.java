package com.shivam.elearn.app.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.shivam.elearn.app.config.SequenceGenerator;
import com.shivam.elearn.app.dto.CategoryDto;
import com.shivam.elearn.app.dto.CoursesDto;
import com.shivam.elearn.app.dto.CustomePageResponse;
import com.shivam.elearn.app.entity.Category;
import com.shivam.elearn.app.entity.Course;
import com.shivam.elearn.app.exception.ResourceNotFoundException;
import com.shivam.elearn.app.repository.CatogryRepo;
import com.shivam.elearn.app.repository.CoursesRepo;
import com.shivam.elearn.app.services.CategoryServices;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class CategoryServiceImpl implements CategoryServices {
	@Autowired
	private CatogryRepo catogryRepo;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private CoursesRepo coursesRepo;

	@Override
	public CategoryDto create(CategoryDto categoryDto) {
        String id=SequenceGenerator.getInstance().nextId();
        log.info("id>>>>> {}",id);
	//	String id = UUID.randomUUID().toString();
		categoryDto.setId(id);
		categoryDto.setAddeDate(new Date());

		Category category = modelMapper.map(categoryDto, Category.class);
		Category save = catogryRepo.save(category);

		return modelMapper.map(save, CategoryDto.class);
	}

	@Override
	public CustomePageResponse<CategoryDto> getAllCategoryDtos(int pageNumber,int pageSize,String sortBy) {
		
		
		Sort sort=Sort.by(sortBy).ascending();
		PageRequest pageRequest = PageRequest.of(pageNumber, pageSize,sort);
		
        Page<Category> catogerypage = catogryRepo.findAll(pageRequest);
        
        List<Category> content = catogerypage.getContent();
        
        
         List<CategoryDto> collect = content.stream().map(cato -> modelMapper.map(cato, CategoryDto.class)).toList();
         
         CustomePageResponse<CategoryDto> customePageResponse=new CustomePageResponse<>();
         customePageResponse.setContent(collect);
         customePageResponse.setLast(catogerypage.isLast());
         customePageResponse.setTotalElement(catogerypage.getTotalElements());
         customePageResponse.setTotalPages(catogerypage.getTotalPages());
         customePageResponse.setPageNumber(catogerypage.getNumber());
         customePageResponse.setPageSize(catogerypage.getSize());
         
		return customePageResponse;
	}

	@Override
	public CategoryDto getSingle(String id) {
		Category category = catogryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Data not found!!"));
		return modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public void delete(String id) {
		
		Category category = catogryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Data not found!!"));
		catogryRepo.delete(category);

	}

	@Override
	public CategoryDto updateCategoryDto(CategoryDto categoryDto, String id) {
		log.info("categirty Dto>>>>>{}",categoryDto);
		Category category = catogryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Data not found!!"));
		category.setTitle(categoryDto.getTitle());
		category.setDesc(categoryDto.getDesc());
		Category save = catogryRepo.save(category);
		log.info("save>>>>> {}",save);
		return modelMapper.map(category, CategoryDto.class);
	}

	@Override
	@Transactional
	public void addCoursesToCategory(String catId, String id) {
		
		Category category = catogryRepo.findById(catId).orElseThrow(() -> new ResourceNotFoundException("Catogry id not found!!"));
		
		Course course = coursesRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Course id not found"));
		
		category.addCourse(course);
		catogryRepo.save(category);
		System.out.println("Category relation update");
		
	}

	@Override
	@Transactional
	public List<CoursesDto> getCoursesOfCat(String id) {
		
		Category category = catogryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category Not found"));
		log.info("category>>>> {}",category);
        List<Course> courses = category.getCourses();
       return  courses.stream().map(course -> modelMapper.map(course,CoursesDto.class)).collect(Collectors.toList());
      
	}

}
