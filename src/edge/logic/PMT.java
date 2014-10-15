package edge.logic;


import edge.models.Project;
import edge.models.User;
import edge.models.GoogleDriveFile;
import edge.models.Todo;

public class PMT {
	
	public static void main(String[] args){
		ConfigParser config = new ConfigParser();
		config.loadConfig(config.DEFAULT_CONFIG);
		

		DatabaseConnector db = new DatabaseConnector();
		db.open();
		
		GoogleDrive drive = new GoogleDrive();
		try {
			drive.authorize();
		} catch (Exception e){
			e.printStackTrace();
		}
		
		System.out.print("ok");
		
		db.close();
	}
}
