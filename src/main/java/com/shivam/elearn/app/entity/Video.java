package com.shivam.elearn.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "video")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Video {
	
	@Id
	private String videoId;
	
	private String title;
	
	@Column(name = "description",length = 1000)
	private String desc;
	
	private String filePath;
	
	private String contentType;
	@ManyToOne
	private Course course;
	
	 
	
	

}
