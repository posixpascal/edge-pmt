package edge.helper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.SessionFactory;

import edge.logic.Database;
import edge.models.*;

public class DatabaseBootstrap {
	
	public static void main(String[] args){
		SessionFactory factory = Database.getSessionFactory();
		
		
		System.out.print("Erstelle Testuser...");
		
		List<HashMap<String, String>> users = new ArrayList();
		
		HashMap<String, String> user1 = new HashMap<String, String>(){{
			put("email", "test@edge-pmt.de");
			put("password", "test1234");
			put("firstname", "Max");
			put("lastname", "Mustermann");
			put("username", "test1");
		}};
		
		HashMap<String, String> user2 = new HashMap<String, String>(){{
			put("email", "test@edge-pmt.de");
			put("password", "test1234");
			put("firstname", "Max");
			put("lastname", "Mustermann");
			put("username", "test2");
		}};
		
		HashMap<String, String> user3 = new HashMap<String, String>(){{
			put("email", "test@edge-pmt.de");
			put("password", "test1234");
			put("firstname", "Max");
			put("lastname", "Mustermann");
			put("username", "test3");
		}};
		
		users.add(user1);
		users.add(user2);
		users.add(user3);
		
		
		users.forEach( (userData) -> {
			
			
			if (User.findByUsername(userData.get("username")) == null){

				User user = new User();
				user.setEMail(userData.get("email"));
				user.setPassword(userData.get("password"));
				user.setFirstname(userData.get("firstname"));
				user.setLastname(userData.get("lastname"));
				user.setUsername(userData.get("username"));
				
				
				Database.saveAndCommit(user);
				
				System.out.println("User: " + user.getUsername());
				System.out.println("Passwort: " + userData.get("password"));
				System.out.println("========");
			}
		});
		
		
		
	}
	
}
