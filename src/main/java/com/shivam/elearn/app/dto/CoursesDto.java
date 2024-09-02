package com.shivam.elearn.app.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CoursesDto {

	private String courseid;

	private String title;

	private String shortDesc;

	private String longDesc;

	private double price;

	private boolean live = false;

	private double discount;

	private String banner;

	private Date addeDate;
	
	private String bannerContentType;

	
	private List<VideoDto> videos = new ArrayList<>();

	
	private List<CategoryDto> categoryList = new ArrayList<>();

}
