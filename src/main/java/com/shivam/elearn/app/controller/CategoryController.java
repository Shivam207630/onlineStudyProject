package com.shivam.elearn.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shivam.elearn.app.config.AppConstant;
import com.shivam.elearn.app.dto.CategoryDto;
import com.shivam.elearn.app.dto.CoursesDto;
import com.shivam.elearn.app.dto.CustomMessage;
import com.shivam.elearn.app.dto.CustomePageResponse;
import com.shivam.elearn.app.services.CategoryServices;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
	@Autowired
	private CategoryServices categoryServices;

	@PostMapping
	public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDto categoryDto) {

//		if (result.hasErrors()) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invilid Data");
//		}

		CategoryDto savecategoryDto = categoryServices.create(categoryDto);

		return ResponseEntity.status(HttpStatus.CREATED).body(savecategoryDto);
	}

	@GetMapping
	public CustomePageResponse<CategoryDto> getAll(
			@RequestParam(value = "pageNumber", required = false, defaultValue = AppConstant.DEFAULT_PAGE_NUMBER) int pageNumber,
			@RequestParam(value = "pageSize", required = false, defaultValue = AppConstant.DEFAULT_PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortBy", required = false, defaultValue = AppConstant.DEFAULT_SORT_BY) String sortBy) {
		CustomePageResponse<CategoryDto> allCategoryDtos = categoryServices.getAllCategoryDtos(pageNumber, pageSize,
				sortBy);
		return allCategoryDtos;
	}

	@GetMapping("/{id}")
	public CategoryDto getSingle(@PathVariable String id) {
		CategoryDto categoryDto = categoryServices.getSingle(id);
		return categoryDto;
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<CustomMessage> delete(@PathVariable String id) {
		categoryServices.delete(id);
		CustomMessage customMessage = new CustomMessage();
		customMessage.setMessage("Delete is sucessfully");
		customMessage.setSucess(true);

		return ResponseEntity.status(HttpStatus.OK).body(customMessage);
	}

	@PutMapping("/{id}")
	public CategoryDto updateCategoryDto(@PathVariable String id, @RequestBody CategoryDto categoryDto) {
		CategoryDto updateCategoryDto = categoryServices.updateCategoryDto(categoryDto, id);
		return updateCategoryDto;
	}

	@PostMapping("/{id}/courses/{courseid}")
	public ResponseEntity<CustomMessage> addCourseToCatogery(@PathVariable String id, @PathVariable String courseid) {
		categoryServices.addCoursesToCategory(id, courseid);
		CustomMessage customMessage = new CustomMessage();
		customMessage.setMessage("Category update");
		customMessage.setSucess(true);
		return ResponseEntity.ok(customMessage);
	}

	@GetMapping("/{id}/courses")
	public ResponseEntity<List<CoursesDto>> getCoursesOfCategory(@PathVariable String id) {
		return ResponseEntity.ok(categoryServices.getCoursesOfCat(id));
	}

}
