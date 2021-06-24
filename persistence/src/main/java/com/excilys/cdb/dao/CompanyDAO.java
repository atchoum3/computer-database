package com.excilys.cdb.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.binding.persistence.CompanyEntity;
import com.excilys.cdb.binding.persistence.ComputerEntity;
import com.excilys.cdb.binding.persistence.mapper.CompanyEntityMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Page;

@Repository
public class CompanyDAO {

	@PersistenceContext
	private EntityManager em;
	private CompanyEntityMapper mapper;
	private CriteriaBuilder cb;

	public CompanyDAO(EntityManagerFactory emf, CompanyEntityMapper mapper) {
		this.mapper = mapper;
		em = emf.createEntityManager();
		cb = em.getCriteriaBuilder();
	}

	public List<Company> getAll() {
		CriteriaQuery<CompanyEntity> cr = cb.createQuery(CompanyEntity.class);
		Root<CompanyEntity> root  = cr.from(CompanyEntity.class);
		cr.orderBy(cb.asc(root.get("name")));

		return mapper.toListCompany(em.createQuery(cr).getResultList());
	}

	public List<Company> getAll(Page page) {
		CriteriaQuery<CompanyEntity> cr = cb.createQuery(CompanyEntity.class);
		Root<CompanyEntity> root = cr.from(CompanyEntity.class);
		cr.orderBy(cb.asc(root.get("name")));

		 List<CompanyEntity> dao = em.createQuery(cr)
				.setFirstResult((page.getCurrentPage() - 1) * page.getNbElementByPage())
				.setMaxResults(page.getNbElementByPage())
				.getResultList();

		return mapper.toListCompany(dao);
	}

	public int count() {
		CriteriaQuery<Long> cr = cb.createQuery(Long.class);
		Root<CompanyEntity> company = cr.from(CompanyEntity.class);
		cr.select(cb.count(company));
		return em.createQuery(cr).getSingleResult().intValue();
	}

	@Transactional
	public int delete(long id) {
		CriteriaDelete<ComputerEntity> computerQuery = cb.createCriteriaDelete(ComputerEntity.class);
		Root<ComputerEntity> rootComputer = computerQuery.from(ComputerEntity.class);
		computerQuery.where(cb.equal(rootComputer.get("company"), id));

		CriteriaDelete<CompanyEntity> companyQuery = cb.createCriteriaDelete(CompanyEntity.class);
		Root<CompanyEntity> rootCompany = companyQuery.from(CompanyEntity.class);
		companyQuery.where(cb.equal(rootCompany.get("id"), id));

		int nbComputerDeleted = em.createQuery(computerQuery).executeUpdate();
		em.createQuery(companyQuery).executeUpdate();
		return nbComputerDeleted;
	}
}