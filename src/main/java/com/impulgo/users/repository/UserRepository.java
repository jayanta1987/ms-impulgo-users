package com.impulgo.users.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.impulgo.users.entity.User;


@Repository
@Transactional
public class UserRepository {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	EntityManager em;

	
	public User findByEmail(String email) {
	    User user = null;
	    try {
	        Query query = em.createQuery("SELECT u FROM User u WHERE u.email = :email");
	        query.setParameter("email", email);
	        user = (User) query.getSingleResult();
	    } finally {
	        em.close();
	    }
	    return user;
	}
	
	public boolean isUserExists(String email) {
	    long userCount = 0;
	    try {
	        Query query = em.createQuery("SELECT COUNT(u.id) FROM User u WHERE u.email = :email");
	        query.setParameter("email", email);
	        userCount = (long) query.getSingleResult();
	    } finally {
	        em.close();
	    }
		    
	    return (userCount>0);
	}

	public User findById(int id) {
		return em.find(User.class, id);
	}

	public void delete(User user) {
		em.remove(user);
	}

	public List<User> findAll() {
		return em.createQuery("select e from User e",
			    User.class).getResultList();
	}

	public void save(User user) {
		 user.setId(null);
		 logger.info("User going to be saved with {}",user);
		 em.persist(user);
	}
	 
	public void update(User user) {
		 em.merge(user);
	}


}
