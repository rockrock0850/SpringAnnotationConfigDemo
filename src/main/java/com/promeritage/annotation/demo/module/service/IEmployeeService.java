package com.promeritage.annotation.demo.module.service;

import java.util.List;

import com.promeritage.annotation.demo.module.model.Employee;
import com.promeritage.annotation.demo.module.vo.form.EmployeeVO;

public interface IEmployeeService {

	public List<Employee> select();

	public Employee select(long id);

	public Employee insert(EmployeeVO vo);

	public Employee update(EmployeeVO vo);

	public void delete(long id);

}
