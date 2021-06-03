package com.excilys.cdb.bindingBack.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.bindingBack.CompanyDTO;
import com.excilys.cdb.bindingBack.ComputerDTO;

public class ComputerDTOMapper {
	private static ComputerDTOMapper instance = new ComputerDTOMapper();

	private ComputerDTOMapper() { }

	private static final long COMPANY_ID_NULL = 0;

	public static ComputerDTOMapper getInstance() {
		return instance;
	}

	/**
	 * Get data from a ResultSet to build a ComputerDTO object.
	 * @param rs a result set object from the SQL relation of computer
	 * @return ComputerDTO object
	 * @throws SQLException
	 */
	public ComputerDTO toComputer(ResultSet rs) throws SQLException {
		String name = rs.getString(2);
		ComputerDTO.Builder builder = new ComputerDTO.Builder(name);

		builder.withId(rs.getLong(1));
		builder.withIntroduced(rs.getString(3));
		builder.withDiscontinued(rs.getString(4));

		long companyId = rs.getLong(5);
		if (companyId != COMPANY_ID_NULL) {
			String companyName = rs.getString(6);
			builder.withCompany(new CompanyDTO(companyId, companyName));
		}
		return builder.build();
	}

	/**
	 * Get data from a ResultSet to fill in the collection.
	 * @param rs a result set object from the SQL relation of computer
	 * @param collection to fill in
	 * @return {@link ComputerDTO} list
	 * @throws SQLException
	 */
	public List<ComputerDTO> toListComputer(ResultSet rs) throws SQLException {
		List<ComputerDTO> list = new ArrayList<>();
		do {
			list.add(toComputer(rs));
		} while (rs.next());
		return list;
	}
}
