package edge.logic;

import org.hibernate.SessionFactory;

import edge.controllers.LoginController;
import edge.controllers.TodoController;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application {
	

	 public static void main(String[] args)
	    {
		 	SessionFactory factory = Database.getSessionFactory();
	        launch(args);
	    }
	 	
	 	private static MainApplication instance;
	 	
	 	public MainApplication()
	 	{
	 		instance = this;
	 	}
	 	
	 	public static MainApplication GetInstance(){
	 		return instance;
	 	}
	 	
	 	
		private Stage rootStage;    
		public Stage getRootStage()
		{
		 		return rootStage;
		}
		
				 	
	    public void setView(String stageTitle, Scene scene) throws Exception
	    {
	    	rootStage.setScene(scene);
	    	rootStage.setTitle(stageTitle);
	        rootStage.show();
	    }
	    
	    public void SetNewView(String stageTitle, Scene scene)
	    {
	    	Stage tempStage = new Stage();
	    	tempStage.setScene(scene);
	    	tempStage.setTitle(stageTitle);
	        tempStage.show();
	    }

	    public void start(Stage primaryStage) throws Exception {
			rootStage = primaryStage;
			
			EdgeFxmlLoader loader = new EdgeFxmlLoader();
	        Parent root = (Parent) loader.load("../views/login.fxml", LoginController.class);
	        Scene scene = new Scene(root, 1306, 703);
	        
	        scene.getStylesheets().add(this.getClass().getResource("../assets/stylesheets/login.css").toString());
	        String StageTitle = "EDGE-PMT - Login";
			setView(StageTitle, scene);
		}
}
