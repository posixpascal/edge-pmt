package edge.controllers.settings;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import edge.controllers.SettingsController;
import edge.helper.EdgeSession;
import edge.models.Settings;

public class EmailSettingsController extends SettingsController {
	@FXML
	private TextField usernameField;
	
	@FXML
	private TextField portField;
	
	@FXML
	private TextField hostField;
	
	@FXML
	private TextField passwordField;
	
	@FXML
	private Text senderField;
	
	@FXML
	private CheckBox receiveEmailsCheckbox;
	
	@FXML
	private void saveEmails(){
		
	}
	
	@FXML
	private void initialize(){
		Settings smtpHost = EdgeSession.getActiveUser().getSettingFor("smtp_host", "localhost");
		Settings smtpPort = EdgeSession.getActiveUser().getSettingFor("smtp_port", "25");
		Settings smtpUser = EdgeSession.getActiveUser().getSettingFor("smtp_user", "postfix");
		Settings smtpPassword = EdgeSession.getActiveUser().getSettingFor("smtp_pass", "yolo");
		Settings smtpSender = EdgeSession.getActiveUser().getSettingFor("smtp_sender", "edge@mailer");
		Settings canReceive = EdgeSession.getActiveUser().getSettingFor("smtp_can_receive", "1");
		
		hostField.setText(smtpHost.getStringValue());
		usernameField.setText(smtpUser.getStringValue());
		portField.setText(smtpPort.getStringValue());
		passwordField.setText(smtpPassword.getStringValue());
		senderField.setText(smtpSender.getStringValue());
		
		if (canReceive.getStringValue().equals("1")){
			receiveEmailsCheckbox.setSelected(true);
		} else {
			receiveEmailsCheckbox.setSelected(false);
		}
	}
}
