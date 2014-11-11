package edge.controllers;




import it.sauronsoftware.ftp4j.FTPAbortedException;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferException;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.FilenameUtils;

import edge.helper.EdgeFTP;
import edge.helper.EdgeSession;
import edge.helper.EdgeTransferListener;
import edge.logic.Database;
import edge.logic.EdgeFxmlLoader;
import edge.logic.MainApplication;
import edge.models.FTPFiles;
import edge.models.Project;
import edge.models.User;
import edge.models.TodoGroup;
import edge.models.Todo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
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
	
	@FXML
	private Text sizeText;
	
	@FXML
	private Text filenameText;
	
	@FXML
	private Text ftpServerText;
	
	@FXML
	private ImageView previewImage;
	
	@FXML
	private TilePane fileTilePane;
	
	@FXML
	private Text speedText;
	
	@FXML
	private ProgressBar progressBar;
	
	public ProjectViewController(Project project) {
		this.initWithProject(project);
	}

	@FXML
	private void createNewTodo(){
		TodoCreateController todoCreateController = new TodoCreateController(project);
		todoCreateController.setParent(this);
		openView("todo_new.fxml", todoCreateController);
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
		
		_initTodos();
		_initFiles();
	}
	
	private File fileToBeUploaded = null;
	
	private void _initFiles(){
		this.project.getFtpFiles().forEach( (ftpFile) -> {
			addFileToGrid(ftpFile);
		});
	}
	
	protected void addFileToGrid(FTPFiles ftpFile){
		GridPane fileBox = new GridPane();
		
		fileBox.addColumn(0);
		fileBox.addRow(0); // filename
		fileBox.addRow(1); // preview
		fileBox.addRow(2); // größe
		fileBox.addRow(3); // uploader
		fileBox.addRow(4); // download button
		
		
		Text fileNameText = new Text();
		fileNameText.setText(ftpFile.getFileName());
		
		Text sizeText = new Text();
		long fSize = ftpFile.getSize();
		double size = Math.round(fSize / 1024);
		
		sizeText.setText("" + size + " kBytes");
		
		
		Text uploaderText = new Text();
		uploaderText.setText(ftpFile.getUser().getFirstname() + " " + ftpFile.getUser().getLastname());
		
		ImageView previewImage = new ImageView();
		previewImage.setImage(byteArrayToImage(ftpFile.getPreview()));
		
		fileBox.add(fileNameText, 0, 0);
		fileBox.add(previewImage, 0, 1);
		fileBox.add(uploaderText, 0, 2);
		fileBox.add(sizeText, 0, 3);
		fileBox.setPadding(new Insets(10, 10, 10, 10));
		
		fileTilePane.getChildren().add(fileBox);
	}
	
	@FXML
	private void selectFileForUpload(){
		fileToBeUploaded = openFileChooser((Stage) this.projectNameLabel.getScene().getWindow());
		if (fileToBeUploaded != null){
			UploadController uploadController = new UploadController();
			uploadController.setParent(this);
			uploadController.setFile(fileToBeUploaded);
			openView("upload.fxml", uploadController);
		} else {
			
		}
	}
	
	protected Project getProject(){
		return this.project;
	}

	

	protected void _initTodos(){
		todoAccordion.getPanes().clear();
		
		Set<TodoGroup> todoGroups = project.getTodoGroups();
		
		todoGroups.forEach( (todoGroup) -> {
			_addTodogroup(todoGroup);
		});	
	}
	
	private void _addTodogroup(TodoGroup todoGroup){
		TitledPane todoGroupPane = new TitledPane();
		todoGroupPane.setText(todoGroup.getTitle());
	
		
		Set<Todo> todos = todoGroup.getTodos();
		
		AnchorPane todoContainer = new AnchorPane();
		GridPane todoGridPane = new GridPane();
		
		todoGridPane.addColumn(4);
		todoGridPane.addRow(todos.size());

		todos.forEach( (todo) -> {
			_addTodo(todo, todoGridPane, todoContainer);
		});
		
		todoGroupPane.setContent(todoContainer);
		todoAccordion.getPanes().add(todoGroupPane);
	}
	
	private void _addTodo(Todo todo, GridPane todoGridPane, AnchorPane todoContainer){
		Text todoTitle = new Text();
		todoTitle.setText(todo.getTitle());
		
		CheckBox todoCompleteCheckbox = new CheckBox();
		if (todo.isClosed()){
			todoCompleteCheckbox.setSelected(true);
		} else {
			todoCompleteCheckbox.setSelected(false);
		}
		
		Text deadlineText = new Text();
		if (todo.getDeadline() != null) deadlineText.setText(todo.getDeadline().toString());
		
		Button openTodoViewBtn = new Button();
		openTodoViewBtn.setText("Todo öffnen");
		
		todoGridPane.paddingProperty().set(new Insets(10, 10, 10, 10));
		
		todoGridPane.add(todoCompleteCheckbox, 0, currentRow);
		todoGridPane.add(todoTitle, 1, currentRow);
		todoGridPane.add(deadlineText, 2, currentRow);
		todoGridPane.add(openTodoViewBtn, 4, currentRow);
		todoGridPane.setPrefWidth(todoContainer.getWidth());
		todoContainer.getChildren().add(todoGridPane);
		
		
		currentRow++;
	}
	
	private int currentRow = 0;
	
	protected void initWithProject(Project project){
		//List<User> users = User.getAll();
		this.project = project;
		
		
	}
	

}
