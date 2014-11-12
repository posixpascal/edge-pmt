package edge.controllers;


import java.io.File;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import edge.helper.EdgeMailer;
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
	
	@FXML
	private void changeAvatar(){
		this.avatar = openImageChooser((Stage) this.titleText.getScene().getWindow());
		if (this.avatar  != null){		
			ImageView t = new ImageView();
			avatarImageView.setImage(new Image(this.avatar.getAbsoluteFile().toURI().toString()));
			
		}
	}
	
	@FXML
	private PasswordField passwordField;
	
	private User transmittedUser;
	
	@FXML
	private void initialize() 
	{
		ImageView t = new ImageView();
		avatarImageView.setImage(new Image(getNoPicturePath()));
		
		if(transmittedUser != null){
			titleText.setText("Bearbeite " + transmittedUser.getUsername());
			emailField.setText(transmittedUser.getEMail());
			usernameField.setText(transmittedUser.getUsername());
			usernameField.setDisable(true);
			
			firstnameField.setText(transmittedUser.getFirstname());
			lastnameField.setText(transmittedUser.getLastname());
			passwordField.setText(transmittedUser.getPassword());
			
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
			Stage stage = (Stage) usernameField.getScene().getWindow();
			
			MainController mainController = ((MainController) this.getParent());
			mainController.refreshUserList();
			
			stage.close();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Hinweis");
			alert.setHeaderText("Benutzername bereits vorhanden.");
			alert.setContentText("Der Benutzername ist bereits vergeben, der Benutzer konnte nicht erstellt werden.");
			alert.showAndWait();
		}
	}
	
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
	
	public UserController(User transmittedUser){
			this.transmittedUser = transmittedUser;
	}
	
	public UserController()
	{
		
	}
}

