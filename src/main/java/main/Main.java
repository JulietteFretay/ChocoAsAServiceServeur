package main;
import static spark.Spark.*;

import org.chocosolver.solver.Model;

import dao.ProblemDAO;
import dao.UserDAO;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

    public static void main(String[] args) {
	    EntityManagerFactory em = Persistence.createEntityManagerFactory("ChocoDB");
	    em.createEntityManager().close();

        port(getHerokuAssignedPort());
        
        get("/hello", (req, res) -> "Hello Heroku World");
        get("/test", new Route() {

			@Override
			public Object handle(Request arg0, Response arg1) throws Exception {
				Model model = new Model("A first model");
				return model.getName();
			}
        	
        });
        
        post("/user", new Route() {

			@Override
			public Object handle(Request request, Response response) throws Exception {
				String userName = (String) request.queryParams("user_name");
				String userPassword = (String) request.queryParams("user_password");
				String userMail = (String) request.queryParams("user_mail");
				UserDAO.getInstance().createUser(userName, userPassword, userMail);
				return userName+" "+userPassword+" "+userMail;
			}
        	
        });
        
        post("/problem", new Route() {

			@Override
			public Object handle(Request request, Response response) throws Exception {
				String xml = (String) request.queryParams("problem_xml");
				ProblemDAO.getInstance().createProblem(xml);
				return " "+xml;
			}
        	
        });
        
        
        
        
        
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

}