package edge.controllers;




import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import edge.logic.Database;
import edge.logic.EdgeFxmlLoader;
import edge.logic.MainApplication;
import edge.models.Project;
import edge.models.User;
import edge.models.TodoGroup;
import edge.models.Todo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ProjectViewController extends BaseController {
	Project project = null;

	@FXML
	private Pane todoChartContainer;
	
	@FXML
	private Accordion todoAccordion;
	
	@FXML
	private Text projectNameLabel;
	
	@FXML
	private ProgressBar projectProgressBar;
	
	@FXML
	private Text todoCountLabel;
	
	@FXML
	private Text coworkerCountLabel;

	@FXML
	private Text deadlineLabel;
	
	public ProjectViewController(Project project) {
		this.initWithProject(project);
	}

	@FXML
	private void createNewTodo(){
		EdgeFxmlLoader loader = new EdgeFxmlLoader("../views/todo_new.fxml");
		TodoCreateController projectViewController = new TodoCreateController(project);
		loader.getRawLoader().setController(projectViewController); // das is wichtig, wie optimieren wir das?
		Stage stage = new Stage();
		Scene scene = null;
		
		try {
			scene = new Scene(
					(Pane) loader.getRawLoader().load()
				);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		try {
			stage.setScene(
				scene
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		stage.show();
	}
	
	@FXML
	private void initialize() {
		this.projectNameLabel.setText("" + project.getName());
		this.todoCountLabel.setText("" + project.getTodos().size());
		this.coworkerCountLabel.setText("" + project.getUsers().size());
		
		if (project.getDeadLine() != null){
			this.deadlineLabel.setText(project.getDaysToDeadLineInWords());
			this.deadlineLabel.getStyleClass().add(project.getDeadlineColorClass());
		}
		
		int openTodos =  project.getOpenTodos().size();
		int closedTodos = project.getClosedTodos().size();
		int totalTodos = openTodos + closedTodos;
		
		ObservableList<PieChart.Data> todoChartData = FXCollections.observableArrayList(
			new PieChart.Data("Offene Todos (" + openTodos + ")", openTodos),
			new PieChart.Data("Geschlossene Todos (" + closedTodos + ")", closedTodos)
		);
		
		PieChart todoChart = new PieChart(todoChartData);
		todoChart.setTitle("Todo Übersicht");
		
		todoChartContainer.getChildren().add(todoChart);
		
		Set<TodoGroup> todoGroups = project.getTodoGroups();
		
		todoGroups.forEach( (todoGroup) -> {
			TitledPane todoGroupPane = new TitledPane();
			todoGroupPane.setText(todoGroup.getTitle());
		
			
			Set<Todo> todos = todoGroup.getTodos();
			
			AnchorPane todoContainer = new AnchorPane();
			GridPane todoGridPane = new GridPane();
			
			todoGridPane.addColumn(4);
			todoGridPane.addRow(0);
			
			todos.forEach( (todo) -> {
				Text todoTitle = new Text();
				todoTitle.setText(todo.getTitle());
				
				CheckBox todoCompleteCheckbox = new CheckBox();
				if (todo.isClosed()){
					todoCompleteCheckbox.setSelected(true);
				} else {
					todoCompleteCheckbox.setSelected(false);
				}
				
				Text deadlineText = new Text();
				deadlineText.setText(todo.getDeadline().toString());
				
				Button openTodoViewBtn = new Button();
				openTodoViewBtn.setText("Todo öffnen");
				
				todoGridPane.add(todoCompleteCheckbox, 0, 0);
				todoGridPane.add(todoTitle, 1, 0);
				todoGridPane.add(deadlineText, 2, 0);
				todoGridPane.add(openTodoViewBtn, 4, 0);
				
			});
			
			todoGroupPane.setContent(todoContainer);
			todoAccordion.getPanes().add(todoGroupPane);
		});
		
				
	}
	
	protected void initWithProject(Project project){
		//List<User> users = User.getAll();
		this.project = project;
		
		
	}
	

}
