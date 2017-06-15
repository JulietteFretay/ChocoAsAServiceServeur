package main;
import static spark.Spark.*;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import org.chocosolver.solver.Model;
import org.dom4j.io.XMLWriter;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import dao.ProblemDAO;
import dao.UserDAO;
import entities.AfficheurFlux;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

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
				
				Document xmlFile = parse(xml);
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				  Transformer transformer = transformerFactory.newTransformer();
				  DOMSource source = new DOMSource(xmlFile);
				  StreamResult streamResult =  new StreamResult(new File("test.xml"));
				  transformer.transform(source, streamResult);
				  
				//ProblemDAO.getInstance().createProblem(xml);
				
				String commande = "java -cp choco-parsers-4.0.3-with-dependencies.jar org.chocosolver.parser.xcsp.ChocoXCSP test.xml";
				try {
					System.out.println("D�but du programme");
					Process p = Runtime.getRuntime().exec(commande);
					AfficheurFlux fluxSortie = new AfficheurFlux(p.getInputStream());
		            AfficheurFlux fluxErreur = new AfficheurFlux(p.getErrorStream());
		            
		            new Thread(fluxSortie).start();
		            new Thread(fluxErreur).start();
		            
					p.waitFor();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Fin du programme");
				return "{ text }";
			}
        	
        });
        
    }
    
    public final static Document parse(String xml) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new InputSource(new StringReader(xml)));
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

}