package com.shivam.elearn.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shivam.elearn.app.entity.Category;

public interface CatogryRepo extends JpaRepository<Category, String>{

}
