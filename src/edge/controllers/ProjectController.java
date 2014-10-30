package edge.controllers;


import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import edge.logic.Database;
import edge.logic.EdgeFxmlLoader;
import edge.logic.MainApplication;
import edge.models.Project;
import edge.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class ProjectController extends BaseController {
	@FXML
	private TextField projectNameField;
	
	@FXML
	private DatePicker deadlineField;
	
	@FXML
	private TextField customerField;
	
	@FXML
	private CheckBox notifyPerEmailCheckBox;
	
	@FXML
	private TableView<User> coworkerTable;
	
	@FXML
	private ToggleButton mobileToggleBtn;
	
	@FXML
	private ToggleButton webToggleBtn;
	
	@FXML
	private Button createProjectBtn;
	
	@FXML
	private void initialize() {
		List<User> users = User.getAll();
	}
	
	@FXML
	public void createTodoStage()
	{
		try
		{
			EdgeFxmlLoader loader = new EdgeFxmlLoader();
	        Parent projectCreate = (Parent) loader.load("../views/Todo.fxml", TodoController.class);
	        Scene scene = new Scene(projectCreate, 575, 250);
	        scene.getStylesheets().add(this.getClass().getResource("../assets/stylesheets/todo.css").toString());
	        String StageTitle = "Todo";
	      
			MainApplication projectCreateWindow = new MainApplication();
			projectCreateWindow.SetNewView(StageTitle, scene); 
				
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
		
	}
	
	@FXML
	private void createProject()
	{
		String projectName = this.projectNameField.getText();
		String customerName = this.customerField.getText();
		
		Project project = new Project();
		project.setCustomerName(customerName);
		project.setName(projectName);

		//project.setDeadline(new Date(deadlineField.getValue().toEpochDay()));
		
		// notify every selected user per mail here
		if (notifyPerEmailCheckBox.isSelected())
		{
			// TODO: implement email features.
			// How about GoogleMails SMTP server?
		}
		
		if (project.isValid())
		{
		
		
			Database.getSession().beginTransaction();
			Database.save(project);
			Database.getSession().getTransaction().commit();
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Hinweis");
			alert.setHeaderText("Projekt '" + projectName + "' für '" + customerName + "' erstellt.");
			alert.setContentText("Das Projekt wurde erfolgreich erstellt. Die Mitarbeiter wurden per E-Mail informiert.");
			alert.showAndWait();
		
		} 
		else 
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Hinweis");
			alert.setHeaderText("Fehler im Formular enthalten!");
			alert.setContentText("Das Projekt konnte nicht gespeichert werden");
			alert.showAndWait();
		}
		
	}
}
