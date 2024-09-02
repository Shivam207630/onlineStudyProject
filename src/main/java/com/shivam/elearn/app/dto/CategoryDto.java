package com.shivam.elearn.app.dto;

import java.util.Date;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
	
	private String id;
    
	@NotEmpty(message = "Title is requird!!")
	@Size(min = 5,max = 500,message = "Title must be add greater than 3 and less than 500")
	private String title;

	@NotEmpty(message = "Discription is requird!!")
	private String desc;
	
	private Date addeDate;
	
	
	
}
