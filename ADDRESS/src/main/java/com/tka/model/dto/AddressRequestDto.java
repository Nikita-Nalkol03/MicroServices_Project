package com.tka.model.dto;

import com.tka.model.enums.AddressType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequestDto {

	private Long id;
	private String street;
	private Long pinCode;
	private String city;
	private String country;
	private AddressType  addressType;
}
