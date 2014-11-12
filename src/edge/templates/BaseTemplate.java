package edge.templates;

import java.util.HashMap;
import java.io.File;

import javafx.stage.FileChooser;
import edge.helper.EdgeTemplate;


public class BaseTemplate extends EdgeTemplate {
	public BaseTemplate(){}
	
	private String rootPath = "classpath:";
	
	@SuppressWarnings("unused")
	private String template = null;
	
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
	
	public File pickFile(){
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Bild zum Projekt hinzufügen");
		fileChooser.getExtensionFilters().addAll(
			new FileChooser.ExtensionFilter("Alle Bilder", "*.*"),
			new FileChooser.ExtensionFilter("JPG", "*.jpg"),
			new FileChooser.ExtensionFilter("PNG", "*.png")
		);
		File theFile = fileChooser.showOpenDialog(null);
		return theFile;
	}
	
	/**
	 * Sets the rootPath of the template.
	 * suppressed warnings because not implemented yet.
	 * @param rootPath the path of the rootPath
	 * @return true if rootPath is a directory. false if it's not
	 */
	@SuppressWarnings("unused")
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
	@SuppressWarnings("unused")
	private boolean makeDirectory(String path){
		File directory = new File(rootPath, path);
		if (directory.exists()){ return false; }
		return directory.mkdirs();
	}
	
	/**
	 * sets the template class for the element
	 * @param classPath a string representing the java class
	 * @return
	 */
	@SuppressWarnings("unused")
	private boolean setTemplate(String classPath){
		this.template = classPath;
		return true;
	}
	
	/**
	 * runs the specific template
	 * @abstract
	 * @return boolean whether the template ran successfully or not.
	 */
	public boolean run(){
		System.out.print("Can't run BaseTemplate. Try using a different template");
		return false;
	}

	@Override
	public void beforeStart() {
	}

	@Override
	public void afterFinish() {
	}

	public HashMap<String, String> getFiles() {
		return files;
	}

	public void setFiles(HashMap<String, String> files) {
		this.files = files;
	}
}
