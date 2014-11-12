package edge.logic;

import javafx.fxml.FXMLLoader;
import edge.helper.EdgeError;
import java.io.IOException;

 
/**
 * Loads a fxml file and attaches a controller to the new view.
 * this is the key bridge between our controller and our views.
 */
public class EdgeFxmlLoader
{
	public EdgeFxmlLoader(){}
	
	/**
	 * constructs a new FXMLLoader with the given <tt>resource</tt>
	 * stores the FXMLLoader in the loader variable.
	 * @param resource any FXML file (as string)
	 */
	public EdgeFxmlLoader(String resource){
		this.loader = new FXMLLoader(getClass().getResource(resource));
	}
	
	private FXMLLoader loader = null;
	
	/**
	 * Returns the row FXMLLoader instance 
	 * @return the active FXMLLoader instance.
	 */
	public FXMLLoader getRawLoader(){
		if (loader == null){ this.loader = new FXMLLoader(); }
		return this.loader;
	}
	
	/**
	 * Loads the fxml file and attaches the controller to it.
	 * @param url a string representing the FXML file.
	 * @param controllerClass the controller class (not instantiated)
	 * @return Stage object with the controllerClass attached
	 * @throws IOException when the View file wasn't found.
	 */
    public Object load(String url, Class<?> controllerClass) throws IOException
    {
        Object instance = null;
     
        try {
			instance = controllerClass.newInstance();
		} catch (InstantiationException e) {
			EdgeError.alertAndExit("Fehler", "Die Anwendung konnte die View: " + url + " nicht laden.");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			EdgeError.alertAndExit("Fehler", "Die Anwendung konnte die View: " + url + " nicht laden.");
			e.printStackTrace();
		}
       
        FXMLLoader loader = new FXMLLoader();
        loader.getNamespace().put("controller", instance);
        return FXMLLoader.load(getClass().getResource(url));   
    }

    /**
     * Sets the controller for the view
     * this method also accepts instantiated controllers.
     * @param theClass any java class
     */
	public void setController(Class<?> theClass) {
		try {
			this.getRawLoader().getNamespace().put("controller", theClass.newInstance());
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
	}
}