package edge.controllers;


import edge.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.text.Text;

public class ProjectController extends BaseController {
	@FXML
	private TextField projectNameField;
	
	@FXML
	private TextField customerField;
	
	@FXML
	private TableView<?> coworkerTable;
	
	@FXML
	private ToggleButton mobileToggleBtn;
	
	@FXML
	private ToggleButton webToggleBtn;
	
	@FXML
	private Button createProjectBtn;
	
	@FXML
	private void initialize() {
		System.out.println("New Project initialized");
		
	}
}
