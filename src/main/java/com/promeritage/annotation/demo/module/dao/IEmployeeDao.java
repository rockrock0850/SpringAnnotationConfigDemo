package com.promeritage.annotation.demo.module.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.promeritage.annotation.demo.module.model.Employee;

public interface IEmployeeDao extends JpaRepository<Employee, Long> {}