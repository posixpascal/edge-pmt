package edge.logic;


import edge.models.Project;

public class PMT {
	
	public static void main(String[] args){
		ConfigParser config = new ConfigParser();
		config.loadConfig(config.DEFAULT_CONFIG);
		

		DatabaseConnector db = new DatabaseConnector();
		db.open();
		
		Project p = new Project();
		p.set("name", "VANKASHHHHHH$$$$");
		p.saveIt();
		
		db.close();
		
		ModuleLoader moduleLoader = new ModuleLoader();
		moduleLoader.fetchAll();
		
		System.out.println("OK");
	

	}
}
