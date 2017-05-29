import static spark.Spark.*;

import org.chocosolver.solver.Model;

import spark.Request;
import spark.Response;
import spark.Route;

public class Main {

    public static void main(String[] args) {
        port(getHerokuAssignedPort());
        get("/test", new Route() {

			@Override
			public Object handle(Request arg0, Response arg1) throws Exception {
				Model model = new Model("A first model");
				return model.getName();
			}
        	
        });
        
        post("/user/create", new Route() {

			@Override
			public Object handle(Request request, Response response) throws Exception {
				String userName = (String) request.attribute("user_name");
				String userPassword = (String) request.attribute("user_password");
				String userMail = (String) request.attribute("user_mail");
				return userName+" "+userPassword+" "+userMail;
			}
        	
        });
        
        get("/hello", (req, res) -> "Hello Heroku World");
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

}