package edge.controllers;

import edge.logic.EdgeFxmlLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
public class BaseController {
	protected void addErrorClass(TextField field){
		field.getStyleClass().add("error");
	}
	
	protected void addErrorClass(PasswordField field){
		field.getStyleClass().add("error");
	}

	protected void addSuccessClass(TextField field){
		field.getStyleClass().add("success");
	}
	
	protected void addSuccessClass(PasswordField field){
		field.getStyleClass().add("success");
	}
	
	protected void openView(String view, Object controller){
		EdgeFxmlLoader loader = new EdgeFxmlLoader("../views/" + view);
		loader.getRawLoader().setController(controller);
		
		Stage stage = new Stage();
		Scene scene = null;
		
		try {
			scene = new Scene(
					(Pane) loader.getRawLoader().load()
				);
			stage.setScene(
					scene
				);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		stage.show();
	}
	
}
