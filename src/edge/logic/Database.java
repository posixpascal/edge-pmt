package edge.logic;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import edge.helper.EdgeError;
import edge.models.BaseModel;

/**
 * Hibernate utility wrapper
 * creates and handles all the hibernate stuff used in our application
 */
public class Database {
	 	private static  SessionFactory sessionFactory;
	  	private static ServiceRegistry serviceRegistry;
	  	
	  	/**
	  	 * used to store active transactions
	  	 */
	  	private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
	  	
	 	/**
	 	 * Initialize hibernate using the hibernate.cfg.xml file
	 	 * @throws ExceptionInInitializerError
	 	 * @return
	 	 */
	    private static SessionFactory configureSessionFactory() {
	    	try {
	    		// loads hibernate.cfg
	            Configuration configuration = new Configuration();
	            configuration.configure();
	            serviceRegistry = new ServiceRegistryBuilder()
	                                 .applySettings(configuration.getProperties())
	                                 .buildServiceRegistry();
	            sessionFactory = configuration.buildSessionFactory(serviceRegistry);

	            return sessionFactory;
	        } catch (HibernateException e) {
	            System.out.append("** Exception in SessionFactory **");
	            e.printStackTrace();
	        	EdgeError.alertAndExit("Konnte keine Verbindung zur Datenbank herstellen.", "Die Verbindung zur Datenbank konnte nicht hergestellt werden. Bitte prüfen Sie ob ein MySQL Server konfiguriert ist.");
	        }
	       return sessionFactory;
	    }
	    
	    static {
	    	try {
	    		sessionFactory = configureSessionFactory();
	    	} catch (Exception e){
	    		System.err.println("Error creating the session factory");
	    		e.printStackTrace();
	    		EdgeError.alertAndExit("Konnte keine Verbindung zur Datenbank herstellen.", "Die Verbindung zur Datenbank konnte nicht hergestellt werden. Bitte prüfen Sie ob ein MySQL Server konfiguriert ist.");
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
	    public static Session getSession() throws HibernateException {
	    	Session session = threadLocal.get();
	    	
	    	if (session == null || !session.isOpen()){
	    		if (sessionFactory == null){
	    			rebuildSessionFactory();
	    		}
	    		session = (sessionFactory != null) ? sessionFactory.openSession() : null;
	    		threadLocal.set(session);
	    	}
	    	
	    	return session;
	    }
	    
	    /**
	     * Reconfigures the session factory.
	     */
	    private static void rebuildSessionFactory(){
	    	try {
	    		sessionFactory = configureSessionFactory();
	    	} catch (Exception e){
	    		e.printStackTrace();
	    		EdgeError.alertAndExit("Konnte keine Datenbank Session herstellen.", "Hibernate war nicht in der Lage eine Datenbank Sitzung zu eröffnen.");
	    	}
	    }
	    
	    
	    /**
	     * closes the sessionFactory. No other database actions are possible
	     * after the session is closed.
	     */
	    public static void closeSession(){
	    	Session session = (Session) threadLocal.get();
	    	threadLocal.set(null);
	    	if (session != null){
	    		session.close();
	    	}
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
	     * @return
	     */
	    public static boolean save(Object hibernateObject){
	    	Database.getSession().saveOrUpdate(hibernateObject);
	    	return true;
	    }
	    
	    /**
	     * Saves/updates and commits the transaction. This instantely saves the object to the database
	     * @param hibernateObject any edge.models Class object which should be saved to db
	     */
	    public static void saveAndCommit(Object hibernateObject){
	    	Transaction transaction = null;
	    	try {
	    		Session session = Database.getSession();
	    		transaction = session.beginTransaction();
	    				
	    	
	    		try {
	    			session.saveOrUpdate(hibernateObject);
	    			transaction.commit();
	    		} catch (Exception ex){
	    			transaction.rollback();
	    			throw ex;
	    		}
	    		
	    	} finally {
	    		Database.closeSession();
	    	}
	    }

	    /**
	     * Dump the model to console for debugging purposes.
	     * @param model any edge model which offers the .dump method
	     */
		public static void dump(BaseModel model) {
			model.dump();
		}
	  
}
