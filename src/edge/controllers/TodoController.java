package edge.controllers;


import java.util.Date;
import java.util.ArrayList;
import java.util.List;

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
import javafx.scene.text.Text;

public class TodoController extends BaseController {
	@FXML
	ComboBox UsernameDropdown;
	
	@FXML
	private TextField titleField;
	
	@FXML
	private DatePicker dateField;
	
	@FXML
	private TextField contentField;
	
	@FXML
	private void initialize() 
	{
		loadUsernames();
	}
	

	@FXML
	private void loadUsernames()
	{
		List<User> users = User.getAll();
		List<String> userList = new ArrayList<String>();
		ObservableList<String> usernames = FXCollections.observableList(userList);
		
		for (int index = 0; index < users.size(); index++)
		{
			usernames.add(users.get(index).getFirstname().toString());
		}

		UsernameDropdown.getItems().clear();
		 UsernameDropdown.setItems(usernames);
	}
	
	@FXML
	public void createTodo()
	{
		String title = this.titleField.getText();
		String content = this.contentField.getText();
		Date deadLine = new Date();
		
		Todo todo = new Todo();
		
		todo.setContent(content);
		todo.setTitleName(title);
		
	
	}

}
