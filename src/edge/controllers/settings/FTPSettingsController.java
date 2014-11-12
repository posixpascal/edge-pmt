package edge.controllers.settings;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import edge.helper.EdgeFTP;
import edge.helper.EdgeSession;
import edge.models.Settings;
import edge.models.User;

public class FTPSettingsController extends SettingsController {
	
	@FXML
	private void checkConnection(){
		EdgeFTP ftp = new EdgeFTP(hostField.getText(), portField.getText(), usernameField.getText(), passwordField.getText());
		
		if (ftp.checkConnection()){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Hinweis");
			alert.setHeaderText("Verbindung erfolgreich hergestellt");
			alert.setContentText("Die Verbindung zum FTP Server ist erfolgreich! EDGE PMT kann dort Dateien archivieren.");
			alert.showAndWait();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Hinweis");
			alert.setHeaderText("Keine Verbindung zum FTP Server");
			alert.setContentText("Die Verbindung zum FTP Server ist gescheitert. Bitte überprüfen Sie ihre Angaben und versuchen Sie es erneut");
			alert.showAndWait();
		}
		
	}
	
	@FXML
	private void saveConnection(){
		User activeUser = EdgeSession.getActiveUser();
		activeUser.saveSetting("ftp_host", hostField.getText());
		activeUser.saveSetting("ftp_port", portField.getText());
		activeUser.saveSetting("ftp_user", usernameField.getText());
		activeUser.saveSetting("ftp_pass", passwordField.getText());
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Hinweis");
		alert.setHeaderText("FTP Einstellungen erfolgreich gesichert..");
		alert.setContentText("Die FTP Einstellungen wurdne aktualisiert. EDGE PMT arbeitet nun mit: " + hostField.getText() + ".");
		alert.showAndWait();
	}
	
	@FXML
	private TextField hostField;
	
	@FXML
	private TextField usernameField;
	
	@FXML
	private TextField passwordField;
	
	@FXML
	private TextField pathField;
	
	@FXML
	private TextField portField;
	
	
	@FXML
	private void initialize(){
		Settings ftpHost = EdgeSession.getActiveUser().getSettingFor("ftp_host", "vankash.de");
		Settings ftpPort = EdgeSession.getActiveUser().getSettingFor("ftp_port", "21");
		Settings ftpUser = EdgeSession.getActiveUser().getSettingFor("ftp_user", "pascal");
		Settings ftpPassword = EdgeSession.getActiveUser().getSettingFor("ftp_pass", "boss");
		Settings ftpPath = EdgeSession.getActiveUser().getSettingFor("ftp_path", "/");
		
		hostField.setText(ftpHost.getStringValue());
		usernameField.setText(ftpUser.getStringValue());
		portField.setText(ftpPort.getStringValue());
		passwordField.setText(ftpPassword.getStringValue());
		pathField.setText(ftpPath.getStringValue());
	}
}
