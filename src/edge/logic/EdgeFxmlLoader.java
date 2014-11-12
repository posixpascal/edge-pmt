package edge.logic;
import javafx.fxml.FXMLLoader;

import org.springframework.context.ApplicationContext;

import edge.controllers.project.ProjectViewController;

import java.io.IOException;
import java.io.InputStream;
 
public class EdgeFxmlLoader
{
	public EdgeFxmlLoader(){}
	public EdgeFxmlLoader(String resource){
		this.loader = new FXMLLoader(getClass().getResource(resource));
	}
	
	private FXMLLoader loader = null;
	
	public FXMLLoader getRawLoader(){
		if (loader == null){ this.loader = new FXMLLoader(); }
		return this.loader;
	}
	
    public Object load(String url, Class<?> controllerClass) throws IOException
    {
        
       
        Object instance = null;
        try {
			instance = controllerClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
       
        FXMLLoader loader = new FXMLLoader();
        loader.getNamespace().put("controller", instance);
        return FXMLLoader.load(getClass().getResource(url));
        
        
    }

	public void setController(Class<?> theClass) {
		try {
			this.getRawLoader().getNamespace().put("controller", theClass.newInstance());
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
	}
}