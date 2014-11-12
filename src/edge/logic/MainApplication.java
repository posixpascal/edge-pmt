package edge.logic;

import java.io.IOException;
import edge.controllers.LoginController;
import edge.helper.EdgeError;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Our lovely entrance class
 * this method is used to start the whole application and is available
 * everywhere through a static interface.
 *
 */
public class MainApplication extends Application {
	
	 /**
	  * Hooks the application with the given arguments.
	  * Creates a connection to the database and launches the application.
	  * @param args
	  */
	 public static void main(String[] args)
	 {
		 	Database.getSessionFactory();
	        launch(args);
	 }
	 	
	 private static MainApplication instance;
	 
	 /**
	  * creates a new main application and stores its reference in itself
	  */
	 public MainApplication()
	 {
	 		instance = this;
	 }
	 
	 /**
	  * @return the instance of the main application
	  */
	 public static MainApplication getInstance(){
	 	return instance;
	 }
	 	
	 	
	private Stage rootStage;  
		
	/**
	 * Sets the rootStage of the application
	 * @return
	 */
	public Stage getRootStage()
	{
	 	return rootStage;
	}
	
	/**
	 * Sets a new view for the rootStage
	 * @param stageTitle the title of the stage
	 * @param scene the view object which will be loaded
	 */
	public void setView(String stageTitle, Scene scene)
	{
	 	rootStage.setScene(scene);
	    rootStage.setTitle(stageTitle);
	    rootStage.show();
	}
	 
	/**
	 * Creates a temporary view
	 * @see BaseController.openView
	 * @param stageTitle the stage title (the text on the window)
	 * @param scenet the view object
	 */
	public void setNewView(String stageTitle, Scene scene)
	{
	  	Stage tempStage = new Stage();
	   	tempStage.setScene(scene);
	   	tempStage.setTitle(stageTitle);
	    tempStage.show();
	}

	/**
	 * Creates
	 */
	public void start(Stage primaryStage) {
	    rootStage = primaryStage;
			
		EdgeFxmlLoader loader = new EdgeFxmlLoader();
	    Parent root;
		try {
			root = (Parent) loader.load("../views/login.fxml", LoginController.class);
			
			Scene scene = new Scene(root, 1306, 703);
		        
			scene.getStylesheets().add(this.getClass().getResource("../assets/stylesheets/login.css").toString());
		    setView("EDGE-PMT - Login", scene);
			
		} catch (IOException e) {
			EdgeError.alertAndExit("Anwendungsfehler", "Konnte die Anwendung nicht starten da die View nicht gefunden wurde.");
			e.printStackTrace();
		}
	  
	}
}
