package com.excilys.cdb.dao;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.binding.persistence.UserEntity;
import com.excilys.cdb.binding.persistence.mapper.UserEntityMapper;
import com.excilys.cdb.model.User;

@Repository
public class UserDAO {

	@PersistenceContext
	private EntityManager em;
	private UserEntityMapper mapper;
	private CriteriaBuilder cb;


	public UserDAO(SessionFactory sessionFactory, UserEntityMapper mapper) {
		this.mapper = mapper;
		em = sessionFactory.createEntityManager();
		cb = em.getCriteriaBuilder();
	}

	public Optional<User> getByUsername(String username) {
		CriteriaQuery<UserEntity> cr = cb.createQuery(UserEntity.class);
		Root<UserEntity> computer = cr.from(UserEntity.class);
		cr.where(cb.equal(computer.get("username"), username));
		return Optional.ofNullable(mapper.toUser(em.createQuery(cr).getSingleResult()));
	}

}
