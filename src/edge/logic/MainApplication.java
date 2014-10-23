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
	 
	    public void start(Stage stage) throws Exception
	    {
	        EdgeFxmlLoader loader = new EdgeFxmlLoader();
	        

	        Parent root = (Parent) loader.load("../views/login.fxml", LoginController.class);
	        Scene scene = new Scene(root, 1306, 703);
	        scene.getStylesheets().add(this.getClass().getResource("../assets/stylesheets/login.css").toString());
	        stage.setScene(scene);
	        stage.setTitle("EDGE - PMT - Login");
	        stage.show();
	    }
	
}
