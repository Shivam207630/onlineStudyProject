package com.shivam.elearn.app.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "catogries")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Category {

	@Id
	private String id;

	private String title;

	@Column(name = "description", length = 1000)
	private String desc;
	@Column(name = "created_date")
	private Date addeDate;

	@ManyToMany(mappedBy = "categoryList",cascade = CascadeType.ALL)
	private List<Course> courses = new ArrayList<>();

	public void addCourse(Course course) {
		courses.add(course);
		course.getCategoryList().add(this);
	}

	public void removeCourse(Course course) {
		courses.remove(course);
		course.getCategoryList().remove(this);
	}

}
