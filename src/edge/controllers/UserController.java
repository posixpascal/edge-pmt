package edge.controllers;


import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import edge.logic.Database;
import edge.models.Project;
import edge.models.Todo;
import edge.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class UserController extends BaseController {
	@FXML
	private TextField usernameField;
	
	
	@FXML
	private PasswordField passwordField;
	
	
	@FXML
	private void initialize() 
	{
	
	}
	

	@FXML
	private void createUser(){
		String username = usernameField.getText();
		String password = passwordField.getText();
		
		if (User.findByUsername(username) == null){
			User theUser = new User();
			theUser.setUsername(username);
			theUser.setPassword(password);
			
			Database.saveAndCommit(theUser);
		}
	}
}
