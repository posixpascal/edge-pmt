package edge.controllers.todo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import edge.controllers.project.ProjectViewController;
import edge.helper.EdgeError;
import edge.logic.Database;
import edge.models.Project;
import edge.models.TodoGroup;
import edge.models.User;
import edge.models.Todo;

public class TodoCreateController extends TodoController {

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
		
		/**
		 * if not a single todogroup was found  for the project
		 * Edge PMT will create a new one and attaches it to the project
		 */
		if (newTodoGroup == null){
			newTodoGroup = new TodoGroup();
			newTodoGroup.setTitle(newTitle);
			this.project.getTodoGroups().add(newTodoGroup);	
			Database.saveAndCommit(newTodoGroup);
		}
		
		
		User theUser = (User) User.findByUsername(usersDropdown.getValue());
		
		if (theUser == null){
			EdgeError.alert("Fehler im Formular enthalten!", "Das Todo konnte nicht gespeichert werden! Es muss einem Mitarbeiter zugewiesen werden.");
		} else {
			/** 
			 * create new todo
			 */
			Todo newTodo = new Todo();
			newTodo.setClosed(false);
			newTodo.setContent(contentEditor.getHtmlText());
			newTodo.setTitleName(titleField.getText());
			
			/**
			 * sets the todo deadline
			 */
			LocalDate t = deadlineField.getValue();
			long timestamp = t.toEpochDay() * 24 * 60 * 60 * 1000;
			Date deadline = new Date(timestamp);
			
			/**
			 * add relationships for the hibernate model
			 */
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
		
	
		List<User> users = User.getAll();
		List<String> userList = new ArrayList<String>();
		ObservableList<String> usernames = FXCollections.observableList(userList);
		
		users.forEach( (user) -> {
			usernames.add(user.getUsername());
		});
		
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
