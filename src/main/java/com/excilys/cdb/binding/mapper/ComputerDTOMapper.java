package com.excilys.cdb.binding.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import com.excilys.cdb.binding.CompanyDTO;
import com.excilys.cdb.binding.ComputerDTO;

public class ComputerDTOMapper {
	private static ComputerDTOMapper instance = new ComputerDTOMapper();
	
	private ComputerDTOMapper() { }
	
	public static ComputerDTOMapper getInstance() {
		return instance;
	}
	
	/**
	 * Get data from a ResultSet to build a Computer object.
	 * @param rs a result set object from the SQL relation of computer
	 * @return Computer object
	 * @throws SQLException
	 */
	public ComputerDTO toComputer(ResultSet rs) throws SQLException {		
		long id = rs.getLong(1);
		String name = rs.getString(2);
		String introduced = rs.getString(3);
		String discontinued = rs.getString(4);
		
		long companyId = rs.getLong(5);
		String companyName = rs.getString(6);
		CompanyDTO companyDTO = new CompanyDTO(companyId, companyName);
		
		return new ComputerDTO.Builder(name).withId(id).withCompany(companyDTO)
				.withIntroduced(introduced).withDiscontinued(discontinued).build();
	}
	
	/**
	 * Get data from a ResultSet to fill in the collection.
	 * @param rs a result set object from the SQL relation of computer
	 * @param collection to fill in
	 * @throws SQLException
	 */
	public void toComputers(ResultSet rs, Collection<ComputerDTO> collection) throws SQLException {
		do {
			collection.add(this.toComputer(rs));
		} while (rs.next());
	}
}
