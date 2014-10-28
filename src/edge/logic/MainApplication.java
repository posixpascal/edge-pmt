package edge.logic;

import org.hibernate.SessionFactory;

import edge.controllers.LoginController;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application {
	

	 public static void main(String[] args)
	    {
		 	SessionFactory factory = HibernateUtil.getSessionFactory();
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
	 	
		 private Stage RootStage;
		    
		 	public Stage GetRootStage()
		 	{
		 		return RootStage;
		 	}
	 	
		 	
	    public void GetView(String StageTitle, Scene scene) throws Exception
	    {
	    	RootStage.setScene(scene);
	        RootStage.setTitle(StageTitle);
	        RootStage.show();
	    }

		public void start(Stage primaryStage) throws Exception {
			this.RootStage = primaryStage;
			EdgeFxmlLoader loader = new EdgeFxmlLoader();
	        Parent root = (Parent) loader.load("../views/login.fxml", LoginController.class);
	        Scene scene = new Scene(root, 1306, 703);
	        
	        scene.getStylesheets().add(this.getClass().getResource("../assets/stylesheets/login.css").toString());
	        String StageTitle = "EDGE-PMT - Login";
			GetView(StageTitle, scene);
		}
}
