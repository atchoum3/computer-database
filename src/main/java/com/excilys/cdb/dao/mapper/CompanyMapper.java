package com.excilys.cdb.dao.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.excilys.cdb.binding.CompanyDTO;
import com.excilys.cdb.model.Company;

public class CompanyMapper {
	private static CompanyMapper instance = new CompanyMapper();
	
	private CompanyMapper() { }
	
	public static CompanyMapper getInstance() {
		return instance;
	}
	
	public Company toCompany(CompanyDTO dto) {
		return new Company(dto.getId(), dto.getName());
	}

	public List<Company> toListCompany(List<CompanyDTO> collectionDTO) {
		return collectionDTO.stream().map(c -> toCompany(c)).collect(Collectors.toList());
	}		
}
