package edge.controllers;

import javafx.scene.control.*;
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
}
