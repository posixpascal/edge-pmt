package edge.controllers;


import java.io.File;

import edge.helper.EdgeError;
import edge.helper.EdgeMailer;
import edge.logic.Database;
import edge.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UserController extends BaseController {
	

	@FXML
	private TextField usernameField;
	
	@FXML
	private TextField firstnameField;
	
	@FXML
	private TextField lastnameField;
	
	@FXML
	private TextField emailField;
	
	@FXML
	private ImageView avatarImageView;
	
	@FXML
	private Button changeAvatarBtn;
	
	@FXML
	private Button createUserBtn;
	
	@FXML
	private Text titleText;
	
	@FXML
	private CheckBox notifyUserCheckbox;
	
	private File avatar = null;
	
	/**
	 * changes the user avatar
	 */
	@FXML
	private void changeAvatar(){
		this.avatar = openImageChooser((Stage) this.titleText.getScene().getWindow());
		if (this.avatar  != null){		
			avatarImageView.setImage(new Image(this.avatar.getAbsoluteFile().toURI().toString()));
			
		}
	}
	
	@FXML
	private PasswordField passwordField;
	
	private User transmittedUser;
	
	@FXML
	private void initialize() 
	{
		avatarImageView.setImage(new Image(getNoPicturePath()));
		
		/**
		 * prüfe ob die view wiederverwendet wird um einen user zu bearbeiten.
		 * 
		 */
		if (transmittedUser != null){
			
			/**
			 * populate the textfields
			 */
			titleText.setText("Bearbeite " + transmittedUser.getUsername());
			emailField.setText(transmittedUser.getEMail());
			usernameField.setText(transmittedUser.getUsername());
			firstnameField.setText(transmittedUser.getFirstname());
			lastnameField.setText(transmittedUser.getLastname());
			passwordField.setText(transmittedUser.getPassword());
			
			usernameField.setDisable(true);
		
			
			if (transmittedUser.getImage() != null){
				avatarImageView.setImage( byteArrayToImage(transmittedUser.getImage()));
			} else {
				avatarImageView.setImage(new Image(getNoPicturePath()));
			}
			
			createUserBtn.setText("Benutzer speichern");
			createUserBtn.setOnAction((m) -> {
				editUser();
			});
		}
	}


	/**
	 * create user if user clicks on the create button
	 */
	@FXML
	private void createUser(){
		String username = usernameField.getText();
		String password = passwordField.getText();
		String firstname = firstnameField.getText();
		String lastname = lastnameField.getText();
		String email = emailField.getText();
		
		if (User.findByUsername(username) == null){
			User theUser = new User();
			theUser.setUsername(username);
			theUser.setPassword(password);
			theUser.setEMail(email);
			theUser.setFirstname(firstname);
			theUser.setLastname(lastname);
			
			if (avatar == null){
				theUser.setImage(imageToByteArray(getNoPictureFile()));
			} else { 
				theUser.setImage(imageToByteArray(avatar));
			}
			
			/**
			 * notify the user about his newly created account (if the checkbox is selected)
			 */
			if (notifyUserCheckbox.isSelected()){
				EdgeMailer mailer = new EdgeMailer(email);
				mailer.setMessage("Ihr Account wurde erstellt.\n\nE-Mail: " + email + "\n" + "Passwort: " + password);
				mailer.setSubject("EDGE-PMT: Ihr Account wurde erfolgreich eingerichtet.");
				mailer.sendMail();
			}
			
			Database.saveAndCommit(theUser);
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Hinweis");
			alert.setHeaderText("Benutzer erfolgreich angelegt.");
			alert.setContentText("Das Benutzerkonto wurde erfolgreich angelegt." + (notifyUserCheckbox.isSelected() ? "Der Benutzer wurde per E-Mail benachrichtigt" : "Der Benutzer wurde nicht benachrichtigt."));
			alert.showAndWait();
			
			MainController mainController = ((MainController) this.getParent());
			mainController.refreshUserList();

			Stage stage = (Stage) usernameField.getScene().getWindow();
			stage.close();
			
		} else {
			EdgeError.alert("Fehler beim Benutzermanagement", "Der Benutzer konnte nicht erstellt werden.");
		}
	}
	
	/**
	 * edits the user and saves changes to the database
	 */
	private void editUser(){
		String password = passwordField.getText();
		String firstname = firstnameField.getText();
		String lastname = lastnameField.getText();
		String email = emailField.getText();

		
		transmittedUser.setPassword(password);
		transmittedUser.setEMail(email);
		transmittedUser.setFirstname(firstname);
		transmittedUser.setLastname(lastname);
		Database.saveAndCommit(transmittedUser);
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Hinweis");
		alert.setHeaderText("Benutzer erfolgreich aktualisiert.");
		alert.showAndWait();
		Stage stage = (Stage) usernameField.getScene().getWindow();
		
		MainController mainController = ((MainController) this.getParent());
		mainController.refreshUserList();
		
		stage.close();
	}
	
	/**
	 * constructs a new userController with the transmittedUser (which will be edited)
	 * @param transmittedUser any existing user object
	 */
	public UserController(User transmittedUser){
			this.transmittedUser = transmittedUser;
	}
	
	public UserController()
	{
		
	}
}

