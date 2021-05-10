package com.excilys.persistence;

import java.util.Optional;
import java.util.Set;

import com.excilys.model.Computer;

public interface IComputerDAO {
	public Set<Computer> getAllComputers();
	public Optional<Computer> getById(long id);
	public void save(Computer c);
	public void update(Computer c);
	public void delete(Computer c);
}
