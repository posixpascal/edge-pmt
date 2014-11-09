package edge.controllers.settings;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import edge.controllers.SettingsController;

public class FTPSettingsController extends SettingsController {
	
	@FXML
	private void checkConnection(){}
	
	@FXML
	private void saveConnection(){}
	
	@FXML
	private TextField hostField;
	
	@FXML
	private TextField usernameField;
	
	@FXML
	private TextField passwordField;
	
	@FXML
	private TextField pathField;
	
	
	
	@FXML
	private void initialize(){
		System.out.print("FTP settings");
	}
}
