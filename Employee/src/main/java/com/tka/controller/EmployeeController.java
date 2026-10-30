package com.tka.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tka.exception.MissingParameterException;
import com.tka.model.dto.EmployeeDto;
import com.tka.service.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
	

	private final EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	@PostMapping("/save")
	public ResponseEntity<EmployeeDto> saveEmployee(@RequestBody EmployeeDto employeeDto){
		EmployeeDto response=employeeService.saveEmployee(employeeDto);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<EmployeeDto> updateEmployee(@RequestBody EmployeeDto employeeDto,@PathVariable Long id){
		EmployeeDto response=employeeService.updateEmployee(id,employeeDto);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable Long id){
		employeeService.deleteEmployee(id);
		return new ResponseEntity<>("Employee deleted successfully",HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<EmployeeDto> getSingleEmployee(@PathVariable Long id){
		EmployeeDto response=employeeService.getSingleEmployee(id);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<Iterable<EmployeeDto>> getAllEmployee(){
		Iterable<EmployeeDto> response=employeeService.getAllEmployees();
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@GetMapping("/get-by-emp-code-and-company-name")
	public ResponseEntity<EmployeeDto> getEmployeeByEmpCodeAndCompanyName(@RequestParam(required =false) String empCode, 
			                                                              @RequestParam(required =false) String companyName){
		List<String> missingParametrs=new ArrayList<>();
		if(empCode==null || empCode.trim().isEmpty()) {
			missingParametrs.add("empCode");
		}
		if(companyName==null || companyName.trim().isEmpty()) {
			missingParametrs.add("companyName");
		}
		if(!missingParametrs.isEmpty()) {
			String finalMessage=missingParametrs.stream().collect(Collectors.joining(","));
			throw new MissingParameterException("please provide "+ finalMessage);
		}
		EmployeeDto response=employeeService.getEmployeeByEmpCodeAndCompanyName(empCode,companyName);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
}
