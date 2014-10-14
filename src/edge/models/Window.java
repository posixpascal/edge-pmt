package edge.models;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.application.Application;

public class Window extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = (AnchorPane)FXMLLoader.load(getClass().getResource("Login.fxml"));
			/* BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Sample.fxml")); */
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
