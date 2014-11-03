package edge.logic;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import edge.models.User;
import edge.models.BaseModel;

public class Database {
	 private static final SessionFactory sessionFactory = buildSessionFactory();
	  
	    private static SessionFactory buildSessionFactory() {
	        try {
	            // Create the SessionFactory from hibernate.cfg.xml
	            return new Configuration().configure().buildSessionFactory();
	        } catch (Throwable ex) {
	            // Make sure you log the exception, as it might be swallowed
	            System.err.println("Initial SessionFactory creation failed." + ex);
	            throw new ExceptionInInitializerError(ex);
	        }
	    }
	  
	    public static SessionFactory getSessionFactory() {
	        return sessionFactory;
	    }
	    
	    public static Session getSession(){
	    	if (Database.sessionFactory.getCurrentSession() == null)
	    		Database.sessionFactory.openSession();
	    		
	    	return Database.sessionFactory.getCurrentSession();
	    }
	    
	    public static void closeSession(){
	    	Database.sessionFactory.close();
	    }
	    
	    public static void shutdown() {
	        getSessionFactory().close();
	    }
	    
	    public static void save(Object hibernateObject){
	    	Session session = Database.getSession();
	    	session.save(hibernateObject);
	    }
	    
	    public static void saveAndCommit(Object hibernateObject){
	    	Database.getSession().beginTransaction();
			Database.save(hibernateObject);
			Database.getSession().getTransaction().commit();
	    }

	    /**
	     * Dump the model to console for debugging purposes.
	     * @param model any edge model which offers the .dump method
	     */
		public static void dump(BaseModel model) {
			model.dump();
		}
	  
}
