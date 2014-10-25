package edge.controllers;

import java.awt.event.ActionEvent;

import edge.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController extends BaseController {
	@FXML
	private Button loginBtn;
	
	@FXML 
	private PasswordField passwordField;
	
	@FXML
	private TextField usernameField;
	
	@FXML
	private void initialize() {
		
		/**
		 * @loginBtn clicked
		 */
		loginBtn.setOnAction( (event) -> {
			
			String username = this.usernameField.getText();
			String password = this.passwordField.getText();
			String md5Password = User.hashPassword(password);
			
			User user = (User) User.findByUsername(username);
			if (user == null){ System.out.print("User not found"); }
			else {
				if (md5Password.equals(user.getPassword())){
					
				} else {
					addErrorClass(usernameField);
					addErrorClass(passwordField);
				}
			}
		});
	}
}
