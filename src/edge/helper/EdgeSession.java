package edge.helper;

import edge.models.User;

/**
 * This (yet) simple class is used to store a reference of the current
 * logged in user. the interface is completely static.
 *
 */
public class EdgeSession {
	private static User activeUser = null;
	
	/**
	 * Sets the active user
	 * @param user any valid edge.models.User object
	 */
	public static void setActiveUser(User user){
		EdgeSession.activeUser = user;
	}
	
	/**
	 * Returns the current active user which is logged in the app.
	 * @return a User object of the current active user
	 */
	public static User getActiveUser(){
		return EdgeSession.activeUser;
	}
	
	/**
	 * checks whether a user is logged in or not.
	 * @return true if logged in, false if not.
	 */
	public static boolean isLoggedIn(){
		return (EdgeSession.activeUser == null ? false : true);
	}
}
