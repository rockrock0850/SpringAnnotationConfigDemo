package com.promeritage.annotation.demo.module.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.promeritage.annotation.demo.common.utils.Constant;
import com.promeritage.annotation.demo.common.utils.ShareTool;
import com.promeritage.annotation.demo.module.dao.IEmployeeDao;
import com.promeritage.annotation.demo.module.model.Employee;
import com.promeritage.annotation.demo.module.service.IEmployeeService;
import com.promeritage.annotation.demo.module.vo.form.EmployeeVO;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

	private Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private IEmployeeDao employeeDao;

	@Override
	@Transactional("transactionManager")
//	@Cacheable("employee")
	public List<Employee> select() {
		try {
			List<Employee> list = employeeDao.findAll();

			return list.size() > 0 ? list : null;
		} catch (Exception e) {
			log.error(e, e);
		}

		return null;
	}

	@Override
	@Transactional("transactionManager")
	public Employee select(long id) {
		try {
			Employee employee = employeeDao.findOne(id);

			return employee == null ? null : employee;
		} catch (Exception e) {
			log.error(e, e);
		}

		return null;
	}

	@Override
	@Transactional("transactionManager")
	public Employee insert(EmployeeVO vo) {
		try {
			Employee employee = new Employee();
			employee.setFirstName(vo.getFirstName());
			employee.setLastName(vo.getLastName());
			employee.setCellPhone(vo.getCellPhone());
			employee.setBirthDate(ShareTool.toDate(vo.getBirthDate(), Constant.DATE_PATTERN1));
			employee = employeeDao.save(employee);

			return employee == null ? null : employee;
		} catch (Exception e) {
			log.error(e, e);
		}

		return null;
	}

	@Override
	@Transactional("transactionManager")
	public Employee update(EmployeeVO vo) {
		try {
			Employee employee = new Employee();
			employee.setId(Long.valueOf(vo.getId()));
			employee.setFirstName(vo.getFirstName());
			employee.setLastName(vo.getLastName());
			employee.setCellPhone(vo.getCellPhone());
			employee.setBirthDate(ShareTool.toDate(vo.getBirthDate(), Constant.DATE_PATTERN1));
			employee = employeeDao.save(employee);

			return employee == null ? null : employee;
		} catch (Exception e) {
			log.error(e, e);
		}

		return null;
	}

	@Override
	@Transactional("transactionManager")
	public void delete(long id) {
		employeeDao.delete(id);
	}

}
