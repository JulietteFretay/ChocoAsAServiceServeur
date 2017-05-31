package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entities.Problem;
import entities.Solution;


public class SolutionDAO {
	private static SolutionDAO instance = new SolutionDAO();
	private static final String PERSISTENCE_UNIT_NAME = "ChocoDB";
	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	  
	public void createSolution(int id, Problem problem) {
		EntityManager em = factory.createEntityManager();
		Solution solution = new Solution(id,problem);
		em.getTransaction().begin();
		em.persist(solution);
		em.getTransaction().commit();
	}

	public static SolutionDAO getInstance() {
		return instance;
	}
}
