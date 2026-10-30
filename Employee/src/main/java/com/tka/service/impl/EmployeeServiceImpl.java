package com.tka.service.impl;

import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.tka.exception.BadRequestException;
import com.tka.exception.ResourceNotFoundException;
import com.tka.model.dto.EmployeeDto;
import com.tka.model.entity.Employee;
import com.tka.repository.EmployeeRepository;
import com.tka.service.EmployeeService;
@Service
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeRepository employeeRepository;
	private final ModelMapper modelMapper;
	
	
	public EmployeeServiceImpl(EmployeeRepository employeeRepository , ModelMapper modelMapper) {
		this.employeeRepository = employeeRepository;
		this.modelMapper=modelMapper;
	}

	//Save employee
	@Override
	public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
		if(employeeDto.getId()!=null) {
			throw new RuntimeException("Employee already exists");
		}
		Employee entity=modelMapper.map(employeeDto, Employee.class);
		Employee savedEntity=employeeRepository.save(entity);
		return modelMapper.map(savedEntity, EmployeeDto.class);
	}

	//Update employee
	@Override
	public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {

	    if(id == null || employeeDto.getId() == null) {
	        throw new  BadRequestException("Please provide employee id");
	    }

	    if(!Objects.equals(id, employeeDto.getId())) {
	        throw new  BadRequestException("Id mismatch");
	    }

	    Employee existingEmployee = employeeRepository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id :"+ id));

	    existingEmployee.setEmpCode(employeeDto.getEmpCode());
	    existingEmployee.setEmpName(employeeDto.getEmpName());
	    existingEmployee.setEmpEmail(employeeDto.getEmpEmail());
	    existingEmployee.setCompanyName(employeeDto.getCompanyName());

	    Employee updatedEmployee = employeeRepository.save(existingEmployee);

	    return modelMapper.map(updatedEmployee, EmployeeDto.class);
	}

	//delete employee
	@Override
	public void deleteEmployee(Long id) {
		Employee employee=employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee not found with id:"+ id));
		employeeRepository.delete(employee);
		
	}

	//get single employee
	@Override
	public EmployeeDto getSingleEmployee(Long id) {
		Employee employee=employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee not found with id:"+ id));
		
		return modelMapper.map(employee, EmployeeDto.class);
	}

	//get all employee
	 @Override
	    public List<EmployeeDto> getAllEmployees() {

	        List<Employee> employees = employeeRepository.findAll();
	        if(employees.isEmpty()) {
	        	throw new ResourceNotFoundException("No Employee found");
	        }
	        return employees.stream()
	                .map(emp -> modelMapper.map(emp, EmployeeDto.class))
	                .toList();
	    }

	@Override
	public EmployeeDto getEmployeeByEmpCodeAndCompanyName(String empCode, String companyName) {
		Employee employee=employeeRepository.findByEmpCodeAndCompanyName(empCode,companyName).orElseThrow(()-> new ResourceNotFoundException("Employee not found with empCode :"+empCode+"and companyName:"+companyName));
		return modelMapper.map(employee, EmployeeDto.class);
	}
	
	

}
