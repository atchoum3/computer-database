package com.excilys.cdb.bindingBack;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "ComputerEntity")
@Table(name = "computer")
public class ComputerEntity {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Column(length = 255)
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;

	@ManyToOne
	@JoinColumn(name = "company_id", referencedColumnName = "id")
	private CompanyEntity company;

	public ComputerEntity() { }

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "ComputerDTO [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued="
				+ discontinued + ", company=" + company + "]";
	}

	public LocalDate getIntroduced() {
		return introduced;
	}

	public LocalDate getDiscontinued() {
		return discontinued;
	}

	public CompanyEntity getCompany() {
		return company;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}

	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}

	public void setCompany(CompanyEntity company) {
		this.company = company;
	}


} 