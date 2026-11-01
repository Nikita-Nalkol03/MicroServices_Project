package com.tka.service.impl;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tka.exception.ResourceNotFoundException;
import com.tka.model.dto.AddressDto;
import com.tka.model.dto.AddressRequest;
import com.tka.model.dto.AddressRequestDto;
import com.tka.model.entity.Address;
import com.tka.repository.AddressRepository;
import com.tka.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {

	Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);

	private final AddressRepository addressRepository;
	private final ModelMapper modelMapper;

	public AddressServiceImpl(AddressRepository addressRepository, ModelMapper modelMapper) {
		this.addressRepository = addressRepository;
		this.modelMapper = modelMapper;
	}

	// save address method
	@Override
	public List<AddressDto> saveAddress(AddressRequest addressRequest) {
		// check if employee exist
		List<Address> listToSave = this.saveOrUpdateAddressRequest(addressRequest);
		List<Address> savedAddress = addressRepository.saveAll(listToSave);

		return savedAddress.stream().map(address -> modelMapper.map(address, AddressDto.class)).toList();
	}

	// UPDATE

	@Override
	public List<AddressDto> updateAddress(AddressRequest addressRequest) {

		// Check if employee exists
		List<Address> addressByEmpId = addressRepository.findAllByEmpId(addressRequest.getEmpId());

		if (addressByEmpId.isEmpty()) {
			log.info("No address found for employee id {}", addressRequest.getEmpId());
			log.info("Creating new address for employee id {}", addressRequest.getEmpId());
		}

		List<Address> listToUpdate = this.saveOrUpdateAddressRequest(addressRequest);

		List<Long> upcomingNonNullIds = listToUpdate.stream().map(Address::getId).filter(Objects::nonNull).toList();

		List<Long> existingIds = addressByEmpId.stream().map(Address::getId).toList();

		List<Long> idsToDelete = existingIds.stream().filter(id -> !upcomingNonNullIds.contains(id)).toList();

		if (!idsToDelete.isEmpty()) {
			addressRepository.deleteAllById(idsToDelete);
		}

		List<Address> updatedAddress = addressRepository.saveAll(listToUpdate);

		return updatedAddress.stream().map(address -> modelMapper.map(address, AddressDto.class)).toList();
	}

	@Override
	public AddressDto getSingleAddress(Long id) {
		Address address=addressRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Address not found with id:"+ id));
		return modelMapper.map(address, AddressDto.class);
	}

	@Override
	public List<AddressDto> getAllAddress() {
		List<Address> all=addressRepository.findAll();
		if(all.isEmpty()) {
			throw new ResourceNotFoundException("No Address found");
		}
		return all.stream().map(address -> modelMapper.map(all, AddressDto.class)).toList();
	}

	@Override
	public void deleteAddress(Long id) {
		Address address=addressRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Address not found with id:"+ id));
		addressRepository.delete(address);

	}

	private List<Address> saveOrUpdateAddressRequest(AddressRequest addressRequest) {
		List<Address> listToSave = new ArrayList<>();
		for (AddressRequestDto addressRequestDto : addressRequest.getAddressRequestDtoList()) {
			Address address = new Address();
			address.setId(addressRequestDto.getId() != null ? addressRequestDto.getId() : null);
			address.setStreet(addressRequestDto.getStreet());
			address.setCity(addressRequestDto.getCity());
			address.setCountry(addressRequestDto.getCountry());
			address.setPinCode(addressRequestDto.getPinCode());
			address.setAddressType(addressRequestDto.getAddressType());
			address.setEmpId(addressRequest.getEmpId());
			listToSave.add(address);
		}
		return listToSave;
	}

}
