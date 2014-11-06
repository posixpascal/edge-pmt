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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ProjectViewController extends BaseController {
	Project project = null;

	@FXML
	private Pane todoChartContainer;
	
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
			Set<Todo> todos = todoGroup.getTodos();
			
		});
		
				
	}
	
	protected void initWithProject(Project project){
		//List<User> users = User.getAll();
		this.project = project;
		
		
	}
	

}
