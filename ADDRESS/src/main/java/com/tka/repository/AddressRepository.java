package com.tka.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tka.model.entity.Address;

public interface AddressRepository extends JpaRepository<Address,Long> {

	List<Address> findAllByEmpId(Long empId);
}
