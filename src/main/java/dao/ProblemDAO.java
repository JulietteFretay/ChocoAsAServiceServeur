package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entities.Problem;


public class ProblemDAO {
	private static ProblemDAO instance = new ProblemDAO();
	private static final String PERSISTENCE_UNIT_NAME = "ChocoDB";
	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	  
	public void createProblem(String xml) {
		EntityManager em = factory.createEntityManager();
		Problem user = new Problem(xml);
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
	}

	public static ProblemDAO getInstance() {
		return instance;
	}
}
