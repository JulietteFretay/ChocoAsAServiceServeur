package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entities.Problem;

import java.io.IOException;


public class ProblemDAO {
	private static ProblemDAO instance = new ProblemDAO();
	private static final String PERSISTENCE_UNIT_NAME = "ChocoDB";
	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	  
	public Problem createProblem(String xml) {
		EntityManager em = factory.createEntityManager();
		Problem problem = null;
		try {
			problem = new Problem();
			em.getTransaction().begin();
			em.persist(problem);
			problem.setFile(xml);
			em.getTransaction().commit();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return problem;

	}

	public static ProblemDAO getInstance() {
		return instance;
	}
}
