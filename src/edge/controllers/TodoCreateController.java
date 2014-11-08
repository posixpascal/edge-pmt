package edge.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.event.ChangeEvent;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import edge.logic.Database;
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
	private ComboBox<String> categoryBox;
	
	
	@FXML
	private void createNewTodo(){

		String newTitle = categoryBox.getValue();

		TodoGroup newTodoGroup = (TodoGroup) TodoGroup.findByTitle(newTitle, this.project);
		
		if (newTodoGroup == null){
			newTodoGroup = new TodoGroup();
			newTodoGroup.setTitle(newTitle);
			this.project.getTodoGroups().add(newTodoGroup);	
			Database.saveAndCommit(newTodoGroup);
		}
		
		
		User theUser = (User) User.findByUsername(usersDropdown.getValue());
		if (theUser == null){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Hinweis");
			alert.setHeaderText("Fehler im Formular enthalten!");
			alert.setContentText("Das Todo konnte nicht gespeichert werden! Es muss einem Mitarbeiter zugewiesen werden.");
			alert.showAndWait();
		} else {
			Todo newTodo = new Todo();
			newTodo.setClosed(false);
			newTodo.setContent(contentEditor.getHtmlText());
			newTodo.setTitleName(titleField.getText());
			
			LocalDate t = deadlineField.getValue();
			long timestamp = t.toEpochDay() * 24 * 60 * 60 * 1000;
			Date deadline = new Date(timestamp);
			newTodo.setDeadline(deadline);
			newTodo.setUser(theUser);
			newTodo.setTodoGroup(newTodoGroup);
			newTodo.setProject(project);
			
			Database.saveAndCommit(newTodo);
			newTodoGroup.getTodos().add(newTodo);
			newTodoGroup.setProject(project);
			
			
			project.getTodos().add(newTodo);
			theUser.getTodos().add(newTodo);
			
			Database.saveAndCommit(newTodoGroup);
			Database.saveAndCommit(project);
			Database.saveAndCommit(theUser);
	
			
		
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Hinweis");
			alert.setHeaderText("Todo '" + newTodo.getTitle() + "' erstellt.");
			alert.setContentText("Todo erfolgreich erstellt. Zugewiesen zu " + theUser.toString() + ".");
			alert.showAndWait();
			Stage stage = (Stage) categoryBox.getScene().getWindow();
			ProjectViewController p = (ProjectViewController) this.getParent();
			p._initTodos();
			stage.close();
			
			
		}
	}
	
	@FXML
	private void initialize(){
		this.projectNameText.setText(project.getName());
		
		// User zur ComboBox hinzufügen. (lul. so funny)
		List<User> users = User.getAll();
		List<String> userList = new ArrayList<String>();
		ObservableList<String> usernames = FXCollections.observableList(userList);
		
		users.forEach( (user) -> {
			usernames.add(user.getUsername());
		});
		
		
		// TodoGroups hinzufügen
		List<TodoGroup> todoGroups = TodoGroup.getAll();
		List<String> todoGroupList = new ArrayList<String>();
		ObservableList<String> todoGroupTitles = FXCollections.observableList(todoGroupList);
		
		todoGroups.forEach((todoGroup) -> {
			todoGroupTitles.add(todoGroup.getTitle());
		});
		
		
		categoryBox.setItems(todoGroupTitles);
		
		

		usersDropdown.getItems().clear();
		usersDropdown.setItems(usernames);
		
	}
}
