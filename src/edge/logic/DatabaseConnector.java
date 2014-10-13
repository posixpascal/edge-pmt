package edge.logic;

import org.javalite.activejdbc.Base;


public class DatabaseConnector {
	public void open(){
		 Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://127.0.0.1/edge", "root", "troopermedia");
	}
	public void close(){
		 Base.close();
	}
}
