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

public class LoginController extends BaseController {
	@FXML
	private Button loginBtn;
	
	@FXML 
	private PasswordField passwordField;
	
	@FXML
	private TextField usernameField;
	
	@FXML
	private Text statusLabel;
	
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
			if (user == null){
				usernameField.getStyleClass().remove("success");
				passwordField.getStyleClass().remove("success");
				passwordField.getStyleClass().remove("error");
				
				addErrorClass(usernameField);
				statusLabel.setText("User nicht gefunden");
				statusLabel.getStyleClass().add("status-error");
			}
			else {
				if (md5Password.equals(user.getPassword())){
					usernameField.getStyleClass().remove("error");
					passwordField.getStyleClass().remove("error");
					addSuccessClass(usernameField);
					addSuccessClass(passwordField);
					
					statusLabel.setText("... nächste View öffnen (TODO)");
					statusLabel.getStyleClass().remove("status-error");
					statusLabel.getStyleClass().add("status-success");
					
					EdgeFxmlLoader loader = new EdgeFxmlLoader();
					try {
			        Parent root = (Parent) loader.load("../views/main.fxml", MainController.class);
			        Scene scene = new Scene(root, 1300, 700);
			        
			        scene.getStylesheets().add(this.getClass().getResource("../assets/stylesheets/main.css").toString());
			        String StageTitle = "EDGE-PMT: Projects";
					MainApplication.GetInstance().setView(StageTitle, scene);
					
					}
					catch(Exception ex)
					{
						System.out.println(ex);
					}
				} else {
					addErrorClass(usernameField);
					addErrorClass(passwordField);
					
					statusLabel.setText("Falsches Passwort");
					statusLabel.getStyleClass().add("status-error");
				}
			}
		});
	}
}
