package edge.helper;

import edge.models.User;

public class EdgeSession {
	private static User activeUser = null;
	
	public static void setActiveUser(User user){
		EdgeSession.activeUser = user;
	}
	
	public static User getActiveUser(){
		return EdgeSession.activeUser;
	}
	
	public static boolean isLoggedIn(){
		return (EdgeSession.activeUser == null ? false : true);
	}
}
