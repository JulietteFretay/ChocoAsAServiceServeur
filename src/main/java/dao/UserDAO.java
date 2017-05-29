package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entities.User;

public class UserDAO {
	private static UserDAO instance = new UserDAO();
	private static final String PERSISTENCE_UNIT_NAME = "ChocoDB";
	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	  
	public void createUser(String login, String password, String mail) {
		EntityManager em = factory.createEntityManager();
		User user = new User(login,password,mail);
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
	}

	public static UserDAO getInstance() {
		return instance;
	}
	
	
}
