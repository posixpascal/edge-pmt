package edge.helper;
import java.util.ArrayList;
import java.util.Date;
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
			put("email", "maxmustermann@mobiletrooper.de");
			put("password", "test1234");
			put("firstname", "Max");
			put("lastname", "Mustermann");
			put("username", "test1");
		}};
		
		HashMap<String, String> user2 = new HashMap<String, String>(){{
			put("email", "jo@mobiletrooper.de");
			put("password", "test1234");
			put("firstname", "Jens");
			put("lastname", "Otto");
			put("username", "jensotto");
		}};
		
		HashMap<String, String> user3 = new HashMap<String, String>(){{
			put("email", "nu@mobiletrooper.de");
			put("password", "test1234");
			put("firstname", "Natascha");
			put("lastname", "Ulm");
			put("username", "nataschaulm");
		}};
		
		HashMap<String, String> user4 = new HashMap<String, String>(){{
			put("email", "pr@mobiletrooper.de");
			put("password", "test1234");
			put("firstname", "Pascal");
			put("lastname", "Raszyk");
			put("username", "pascalraszyk");
		}};
		
		
		users.add(user1);
		users.add(user2);
		users.add(user3);
		users.add(user4);
		
		
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

		
		Project project = new Project();
		
		project.setName("Testprojekt");
		project.setCustomerName("Testkunde");
		project.getUsers().add((User) User.findByUsername("test1"));
		project.getUsers().add((User) User.findByUsername("pascalraszyk"));
		
		Database.saveAndCommit(project);
		
		TodoGroup tg = new TodoGroup();
		tg.setTitle("Design");
		tg.setProject(project);
		Database.saveAndCommit(tg);
		
		Todo testTodo = new Todo();
		testTodo.setProject(project);
		testTodo.setTitleName("Vankash macht Design");
		testTodo.setContent("<h1>pls mach design</h1>");
		testTodo.setUser((User) User.findByUsername("test1"));
		testTodo.setTodoGroup(tg);
		Database.saveAndCommit(testTodo);
		
	
		tg.getTodos().add(testTodo);
		
		project.getTodos().add(testTodo);
		User user = ((User) User.findByUsername("pascalraszyk"));
		user.getTodos().add(testTodo);
		user.getProjects().add(project);
		
		project.getTodoGroups().add(tg);
		Database.saveAndCommit(project);
		Database.saveAndCommit(tg);
		Database.saveAndCommit(user);
		
		
		
	}
	
}
