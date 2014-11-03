package edge.templates;

import java.util.HashMap;
import java.io.File;
public class BaseTemplate {
	public BaseTemplate(){}
	
	private String rootPath = "classpath:";
	
	/**
	 * A hashmap which consists of a fileName and the fileContent.
	 * the run() method should iterate over this HashMap and create
	 * the files accordingly.
	 */
	private HashMap<String, String> files = new HashMap<String, String>(){
		private static final long serialVersionUID = 4396856278328549766L;

		
		{
		
		
		}
	};
	
	/**
	 * Sets the rootPath of the template
	 * @param rootPath the path of the rootPath
	 * @return true if rootPath is a directory. false if it's not
	 * @author pr
	 */
	private boolean setRootPath(String rootPath){
		this.rootPath = rootPath;
		return new File(rootPath).isDirectory();
	}
	
	/**
	 * Creates directories <tt>path</tt>
	 * @param path the string which directorys shall be created
	 * @return true if the directory are created successfully. false if
	 * directory already exists.
	 */
	private boolean makeDirectory(String path){
		File directory = new File(rootPath, path);
		if (directory.exists()){ return false; }
		return directory.mkdirs();
	}
	
	/**
	 * runs the specific template
	 * @abstract
	 * @return boolean whether the template ran successfully or not.
	 */
	private boolean run(){
		System.out.print("Can't run BaseTemplate. Try using a different template");
		return false;
	}
}
