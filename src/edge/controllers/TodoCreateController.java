package edge.controllers;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;
import edge.models.Project;
import edge.models.TodoGroup;
import edge.models.User;
import edge.models.Todo;

public class TodoCreateController extends BaseController {

	Project project = null;
	
	public TodoCreateController(Project project) {
		this.project = project;
		
	}
	
	@FXML
	private Text projectNameText;
	
	@FXML
	private DatePicker deadlineField;
	
	@FXML
	private TextField titleField;
	
	@FXML
	private ComboBox<String> usersDropdown;
	
	@FXML
	private HTMLEditor contentEditor;
	
	@FXML
	private ComboBox<TodoGroup> categoryBox;
	
	
	@FXML
	private void createNewTodo(){
		System.out.print("erstelle todo + todogroup");
	}
	
	@FXML
	private void initialize(){
		this.projectNameText.setText(project.getName());
		
		// User zur ComboBox hinzufügen. (lul. so funny)
		List<User> users = User.getAll();
		List<String> userList = new ArrayList<String>();
		ObservableList<String> usernames = FXCollections.observableList(userList);
		
		users.forEach( (user) -> {
			usernames.add(user.toString());
		});
		
		
		// TodoGroups hinzufügen
		List<TodoGroup> todoGroups = TodoGroup.getAll();
		List<String> todoGroupList = new ArrayList<String>();
		ObservableList<String> todoGroupTitles = FXCollections.observableList(todoGroupList);
		
		todoGroups.forEach((todoGroup) -> {
			todoGroupTitles.add(todoGroup.getTitle());
		});
		
		
		

		usersDropdown.getItems().clear();
		usersDropdown.setItems(usernames);
		
	}
}
