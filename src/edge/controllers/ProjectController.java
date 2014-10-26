package edge.controllers;


import java.util.ArrayList;
import java.util.List;

import edge.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
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
	private TableView<String> coworkerTable;
	
	@FXML
	private ToggleButton mobileToggleBtn;
	
	@FXML
	private ToggleButton webToggleBtn;
	
	@FXML
	private Button createProjectBtn;
	
	@FXML
	private void initialize() {
		List<User> users = User.getAll();
		
		// TODO: how the fuck am I able to add cells to this FUCKIN table.
		users.forEach((user) -> {
			List<String> userRow = new ArrayList<String>(5);
			userRow.add(user.getUsername());
			userRow.add("Hello");
			userRow.add("World");
			userRow.add("yo");
			coworkerTable.getItems().addAll(userRow);
			
		});
		
		
		
		
	}
	
	@FXML
	private void createProject(){
		String projectName = this.projectNameField.getText();
		String customerName = this.customerField.getText();
		
	}
}
