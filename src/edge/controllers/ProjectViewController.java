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
			
		});
	}
	
	@FXML
	private void selectFileForUpload(){
		fileToBeUploaded = openFileChooser((Stage) this.projectNameLabel.getScene().getWindow());
		if (fileToBeUploaded != null){
			sizeText.setText(""+fileToBeUploaded.length());
			filenameText.setText(fileToBeUploaded.getName());
			ftpServerText.setText(EdgeSession.getActiveUser().getSettingFor("ftp_host", "vankash.de").getStringValue());
			
			String extension = FilenameUtils.getExtension(fileToBeUploaded.getAbsolutePath());
			previewImage.setImage(new Image(fromFileExtension(extension).getAbsoluteFile().toURI().toString()));
			
		} else {
			sizeText.setText("");
			filenameText.setText("");
			ftpServerText.setText("");
		}
	}
	
	@FXML
	private void startUpload(){
		String host = EdgeSession.getActiveUser().getSettingFor("ftp_host", null).getStringValue();
		String port = EdgeSession.getActiveUser().getSettingFor("ftp_port", null).getStringValue();
		String user = EdgeSession.getActiveUser().getSettingFor("ftp_user", null).getStringValue();
		String pass = EdgeSession.getActiveUser().getSettingFor("ftp_pass", null).getStringValue();
		EdgeFTP ftp = new EdgeFTP(host, port, user, pass);
		ftp.connect();
		try {
			EdgeTransferListener transferListener = new EdgeTransferListener();
			transferListener.setSpeed(this.speedText);
			transferListener.setProgressBar(this.progressBar);
			((FTPClient) ftp.getClient()).upload(fileToBeUploaded, transferListener);
			
			FTPFiles ftpFile = new FTPFiles();
			ftpFile.setFileName(fileToBeUploaded.getName());
			ftpFile.setProject(project);
			ftpFile.setUser(EdgeSession.getActiveUser());
			
			String extension = FilenameUtils.getExtension(fileToBeUploaded.getAbsolutePath());
			ftpFile.setPreview(imageToByteArray(fromFileExtension(extension)));
			ftpFile.setUrl(EdgeSession.getActiveUser().getSettingFor("ftp_path", null).getStringValue());
			Database.saveAndCommit(ftpFile);
			
			this.project.getFtpFiles().add(ftpFile);
			
			EdgeSession.getActiveUser().getFtpFiles().add(ftpFile);
			Database.saveAndCommit(ftpFile);
			Database.saveAndCommit(project);
			Database.saveAndCommit(EdgeSession.getActiveUser());
			
		} catch (IllegalStateException | IOException | FTPIllegalReplyException
				| FTPException | FTPDataTransferException | FTPAbortedException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Hinweis");
			alert.setHeaderText("Fehler beim Upload der Datei");
			alert.setContentText("Beim Upload ist ein Fehler unterlaufen. Konnte Datei nicht hochladen.");
			alert.showAndWait();
			e.printStackTrace();
		}	
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
