package com.shivam.elearn.app.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "courses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Course {
	@Id
	private String courseid;

	private String title;

	@Column(name = "short_description")
	private String shortDesc;

	@Column(name = "long_description", length = 1000)
	private String longDesc;

	private double price;

	private boolean live = false;

	private double discount;

	private String banner;

	private String bannerContentType;

	@Column(name = "created_date")
	private Date addeDate;

	// videos
	@OneToMany(mappedBy = "course")
	private List<Video> videos = new ArrayList<>();

	//
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Category> categoryList = new ArrayList<>();

	public void addCategory(Category category) {
		categoryList.add(category);
		category.getCourses().add(this);
	}

	public void removeCategory(Category category) {
		categoryList.remove(category);
		category.getCourses().remove(category);
	}

}
