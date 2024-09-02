package com.shivam.elearn.app.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class User {
	
	@Id
	private String userId;
	
	private String name;
	
	private String email;
	
	private String phoneNumber;
	
	private String password;
	@Column(nullable = false,length = 1000)
	private String about;
	
	private boolean active;
	
	private boolean emailVeryFied;
	
	private boolean smsVeryFied;
	
	private Date createDate;
	
	private String profile;
	
	private String recentOtp;
	
	

}
