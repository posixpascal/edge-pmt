package edge.logic;

import edge.models.Project;
import edge.models.User;

import org.hibernate.*;



public class PMT {
	
	public static void main(String[] args){
		ConfigParser config = new ConfigParser();
		config.loadConfig(config.DEFAULT_CONFIG);
		

		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		Project project = new Project();
		project.setName("EDGE");
					  
		session.save(project);
		session.getTransaction().commit();
		
	}
}
