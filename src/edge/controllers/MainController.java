package edge.controllers;

import edge.logic.MainApplication;
import edge.logic.EdgeFxmlLoader;
import edge.models.User;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class MainController extends BaseController {
	
	@FXML
	public void createNewStage(){
		try
		{
			EdgeFxmlLoader loader = new EdgeFxmlLoader();
	        Parent projectCreate = (Parent) loader.load("../views/project_new.fxml", ProjectController.class);
	        Scene scene = new Scene(projectCreate, 425, 650);
	        scene.getStylesheets().add(this.getClass().getResource("../assets/stylesheets/project.css").toString());
	        String StageTitle = "Create a new Project";
	       Boolean alreadyopen = false;
	        if (alreadyopen == false)
	        {
			MainApplication projectCreateWindow = new MainApplication();
			projectCreateWindow.SetNewView(StageTitle, scene);
			alreadyopen = true;
			}	
		}
		catch(Exception ex)
		{
			
		}
	}
}
