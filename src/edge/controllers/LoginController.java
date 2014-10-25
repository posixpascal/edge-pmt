package edge.controllers;


import edge.models.User;
import javafx.fxml.FXML;
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
				// TODO: bisschen redundant.
				usernameField.getStyleClass().remove("success");
				passwordField.getStyleClass().remove("success");
				passwordField.getStyleClass().remove("error");
				
				addErrorClass(usernameField);
				statusLabel.setText("User nicht gefunden");
				statusLabel.getStyleClass().add("status-error");
			}
			else {
				if (md5Password.equals(user.getPassword())){
					// TODO: ziemlich redundant...
					usernameField.getStyleClass().remove("error");
					passwordField.getStyleClass().remove("error");
					addSuccessClass(usernameField);
					addSuccessClass(passwordField);
					
					statusLabel.setText("... nächste View öffnen (TODO)");
					statusLabel.getStyleClass().remove("status-error");
					statusLabel.getStyleClass().add("status-success");
				} else {
					// TODO: told ya it's redundant...
					usernameField.getStyleClass().remove("success");
					passwordField.getStyleClass().remove("success");
					addErrorClass(usernameField);
					addErrorClass(passwordField);
					
					statusLabel.setText("Falsches Passwort");
					statusLabel.getStyleClass().add("status-error");
				}
			}
		});
	}
}
