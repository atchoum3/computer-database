package com.excilys.cdb.dao;

import com.excilys.cdb.bindingBack.ComputerEntity;
import com.excilys.cdb.bindingBack.mapper.ComputerEntityMapper;
import com.excilys.cdb.exception.ComputerCompanyIdException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class ComputerDAO {

	private ComputerEntityMapper mapper;
	private CriteriaBuilder cb;
	private EntityManager em;
	private SessionFactory sessionFactory;

	public ComputerDAO(SessionFactory sessionFactory, ComputerEntityMapper mapper) {
		this.mapper = mapper;
		this.sessionFactory = sessionFactory;
		em = this.sessionFactory.createEntityManager();
		cb = em.getCriteriaBuilder();
	}

	public List<Computer> searchByName(String name, Page page) {
		CriteriaQuery<ComputerEntity> cr = cb.createQuery(ComputerEntity.class);
		Root<ComputerEntity> root = cr.from(ComputerEntity.class);
		cr.orderBy(cb.asc(root.get("name")));
		
		 List<ComputerEntity> dao = em.createQuery(cr)
				.setFirstResult((page.getCurrentPage() - 1) * page.getNbElementByPage())
				.setMaxResults(page.getNbElementByPage())
				.getResultList();
		
		return mapper.toListComputer(dao);
	}

	public Optional<Computer> getById(long id) {
		CriteriaQuery<ComputerEntity> cr = cb.createQuery(ComputerEntity.class);
		Root<ComputerEntity> computer = cr.from(ComputerEntity.class);
		cr.where(cb.equal(computer.get("id"), id));
		return Optional.ofNullable(mapper.toComputer(em.createQuery(cr).getSingleResult()));
	}

	public void create(Computer computer) throws ComputerCompanyIdException {
		ComputerEntity computerEntity = mapper.toComputerDTO(computer);
		
		try (Session session = sessionFactory.openSession()) {
			Transaction tx = session.beginTransaction();
			session.persist(computerEntity);
			tx.commit();
		}
	}

	@Transactional
	public void update(Computer computer) throws ComputerCompanyIdException {
		ComputerEntity computerEntity = mapper.toComputerDTO(computer);
		
		
		CriteriaUpdate<ComputerEntity> cr = cb.createCriteriaUpdate(ComputerEntity.class);
		Root<ComputerEntity> root = cr.from(ComputerEntity.class);
		cr.set("name", computerEntity.getName())
				.set(root.get("introduced"), computerEntity.getIntroduced())
				.set(root.get("discontinued"), computerEntity.getDiscontinued())
				.set(root.get("company"), computerEntity.getCompany())
				.where(cb.equal(root.get("id"), computerEntity.getId()));
		
		Session session = sessionFactory.getCurrentSession();
		em.createQuery(cr).executeUpdate();
		session.close();
	}


	public int delete(long id) {
		CriteriaDelete<ComputerEntity> cr = cb.createCriteriaDelete(ComputerEntity.class);
		Root<ComputerEntity> computer = cr.from(ComputerEntity.class);
		cr.where(cb.equal(computer.get("id"), id));
		return em.createQuery(cr).executeUpdate();
	}

	public int countSearchByName(String name) {
		CriteriaQuery<Long> cr = cb.createQuery(Long.class);
		Root<ComputerEntity> rootComputer = cr.from(ComputerEntity.class);
		EntityType<ComputerEntity> typeComputer = em.getMetamodel().entity(ComputerEntity.class);
		
		cr.where(cb.like(cb.lower(rootComputer.get(typeComputer.getDeclaredSingularAttribute("name", String.class))), "%" + name.toLowerCase() + "%"));
		
		cr.select(cb.count(rootComputer));
		return em.createQuery(cr).getSingleResult().intValue();
	}
}

