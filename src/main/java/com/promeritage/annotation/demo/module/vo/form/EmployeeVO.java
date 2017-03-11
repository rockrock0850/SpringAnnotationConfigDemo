package com.promeritage.annotation.demo.module.vo.form;

import org.hibernate.validator.constraints.NotBlank;

public class EmployeeVO{
	
	private Long id;
	
	@NotBlank(message = "{NotBlank.employeeVO.firstName}")
	private String firstName;

	@NotBlank(message = "{NotBlank.employeeVO.lastName}")
	private String lastName;
	
	private String birthDate;

	private String cellPhone;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	
}
