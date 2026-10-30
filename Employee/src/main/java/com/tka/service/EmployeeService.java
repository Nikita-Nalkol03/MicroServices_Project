package com.tka.service;

import java.util.List;

import com.tka.model.dto.EmployeeDto;

public interface EmployeeService {

	EmployeeDto saveEmployee(EmployeeDto employeeDto);
	
	EmployeeDto updateEmployee(Long id , EmployeeDto employeeDto);
	
	void deleteEmployee(Long id);
	
	EmployeeDto getSingleEmployee(Long id);
	
	List<EmployeeDto> getAllEmployees();

	EmployeeDto getEmployeeByEmpCodeAndCompanyName(String empCode, String companyName);
}
