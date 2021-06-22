package com.excilys.cdb.dao;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.bindingBack.UserEntity;
import com.excilys.cdb.bindingBack.mapper.UserEntityMapper;
import com.excilys.cdb.model.User;

@Repository
public class UserDAO {

	/*@PersistenceContext
	private EntityManager em;
	private UserEntityMapper mapper;
	private CriteriaBuilder cb;
	private SessionFactory sessionFactory;*/


	/*public UserDAO(SessionFactory sessionFactory, UserEntityMapper mapper) {
		System.out.println("constructor DAO" + sessionFactory + "," + mapper);
		this.sessionFactory = sessionFactory;
		this.mapper = mapper;
		em = this.sessionFactory.createEntityManager();
		cb = em.getCriteriaBuilder();
		System.out.println("end constructor DAO" + em + "," + this.mapper + "," + cb + "," + this.sessionFactory);
	}*/


	public Optional<User> getByUsername(String username) {
		return Optional.empty();
		/*em = sessionFactory.createEntityManager();
		cb = em.getCriteriaBuilder();

		System.out.println("getByUsername");
		CriteriaQuery<UserEntity> cr = cb.createQuery(UserEntity.class);
		Root<UserEntity> computer = cr.from(UserEntity.class);
		cr.where(cb.equal(computer.get("username"), username));
		return Optional.ofNullable(mapper.toUser(em.createQuery(cr).getSingleResult()));*/
	}

}
