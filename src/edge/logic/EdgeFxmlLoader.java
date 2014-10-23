package edge.logic;
import javafx.fxml.FXMLLoader;

import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.io.InputStream;
 
public class EdgeFxmlLoader
{
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
}