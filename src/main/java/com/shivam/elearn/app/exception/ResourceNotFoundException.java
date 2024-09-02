package com.shivam.elearn.app.exception;

public class ResourceNotFoundException extends RuntimeException{
	
	public ResourceNotFoundException() {
		super("Data Not found!!");
	}
	
	
	public ResourceNotFoundException(String courseNotFound) {
		
		super(courseNotFound);
		
	}

}
