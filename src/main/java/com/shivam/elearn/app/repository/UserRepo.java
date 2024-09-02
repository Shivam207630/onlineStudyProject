package com.shivam.elearn.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shivam.elearn.app.entity.User;

public interface UserRepo extends JpaRepository<User, String>{

}
