package edge.logic;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import edge.models.User;
import edge.models.BaseModel;

/**
 * Hibernate utility wrapper
 * creates and handles all the hibernate stuff used in our application
 */
public class Database {
	 private static final SessionFactory sessionFactory = buildSessionFactory();
	  
	 	/**
	 	 * Initialize hibernate using the hibernate.cfg.xml file
	 	 * @throws ExceptionInInitializerError
	 	 * @return
	 	 */
	    private static SessionFactory buildSessionFactory() {
	        try {
	            return new Configuration().configure().buildSessionFactory();
	        } catch (Throwable ex) {
	            System.err.println("Initial SessionFactory creation failed." + ex);
	            throw new ExceptionInInitializerError(ex);
	        }
	    }
	  
	    /**
	     * Access the current open session
	     * @return SessionFactory
	     */
	    public static SessionFactory getSessionFactory() {
	        return sessionFactory;
	    }
	    
	    /**
	     * Function to access the session singleton.
	     * Returns the session Object of the current open session.
	     * if none session is open at the time it creates it.
	     * @return Session
	     */
	    public static Session getSession(){
	    	if (Database.sessionFactory.getCurrentSession() == null)
	    		Database.sessionFactory.openSession();
	    		
	    	return Database.sessionFactory.getCurrentSession();
	    }
	    
	    /**
	     * closes the sessionFactory. No other database actions are possible
	     * after the session is closed.
	     */
	    public static void closeSession(){
	    	Database.sessionFactory.close();
	    }
	    
	    /**
	     * closes anonymous sessionFactory (alias of closeSession most of the time)
	     */
	    public static void shutdown() {
	        getSessionFactory().close();
	    }
	    
	    /**
	     * saves the hibernateObject to the database using the active session
	     * @param hibernateObject any edge.models Class object which should be saved to db
	     * @return boolean whether the object was an edge.model or not.
	     */
	    public static boolean save(Object hibernateObject){
	    	Session session = Database.getSession();
	    	session.save(hibernateObject);
	    	
	    	return true;
	    }
	    
	    /**
	     * Saves and commits the transaction. This instantely saves the object to the database
	     * @param hibernateObject any edge.models Class object which should be saved to db
	     */
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
