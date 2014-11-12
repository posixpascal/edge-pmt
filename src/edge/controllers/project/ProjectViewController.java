package edge.controllers.project;

import java.io.File;
import java.util.Set;
import edge.controllers.UploadController;
import edge.controllers.todo.TodoCreateController;
import edge.controllers.todo.TodoViewController;
import edge.models.FTPFiles;
import edge.models.Project;
import edge.models.TodoGroup;
import edge.models.Todo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ProjectViewController extends ProjectController {
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
		//int totalTodos = openTodos + closedTodos;
		
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
	
	/**
	 * display uploaded files
	 */
	private void _initFiles(){
		this.project.getFtpFiles().forEach( (ftpFile) -> {
			addFileToGrid(ftpFile);
		});
	}
	
	/**
	 * adds a ftpFile to the TilePane.
	 * @param ftpFile any valid FTPFiles model
	 */
	public void addFileToGrid(FTPFiles ftpFile){
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
	
	/**
	 * selects a file which will be uploaded
	 */
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
	
	/**
	 * gets the active project
	 * @return a Project model
	 */
	public Project getProject(){
		return this.project;
	}

	

	/**
	 * inits the todoGroups for this project.
	 */
	public void _initTodos(){
		todoAccordion.getPanes().clear();
		
		Set<TodoGroup> todoGroups = project.getTodoGroups();
		
		todoGroups.forEach( (todoGroup) -> {
			_addTodogroup(todoGroup);
		});	
	}
	
	/**
	 * adds a todoGroup to the todoAccordion
	 * @param todoGroup any valid TodoGroup model
	 */
	private void _addTodogroup(TodoGroup todoGroup){
		TitledPane todoGroupPane = new TitledPane();
		todoGroupPane.setText(todoGroup.getTitle());
	
		
		Set<Todo> todos = todoGroup.getTodos();
		
		AnchorPane todoContainer = new AnchorPane();
		GridPane todoGridPane = new GridPane();
		
		todoGridPane.addColumn(4);
		todoGridPane.addRow(todos.size());
		
		currentRow = 0;
		todos.forEach( (todo) -> {
			_addTodo(todo, todoGridPane, todoContainer, currentRow++);
		});
		
		todoGroupPane.setContent(todoContainer);
		todoAccordion.getPanes().add(todoGroupPane);
	}
	
	/**
	 * adds a todo to the current todoGridPane
	 * @param todo any valid todo model
	 * @param todoGridPane the parents gridPane
	 * @param todoContainer the anchorPane
	 * @param currentRow the current row where the comment will be placed
	 */
	private void _addTodo(Todo todo, GridPane todoGridPane, AnchorPane todoContainer, int currentRow){
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
		openTodoViewBtn.setOnAction( (m) -> {
			TodoViewController todoViewController = new TodoViewController(todo, project);
			todoViewController.setParent(this);
			
			openView("todo_view.fxml", todoViewController);
		});
		
		todoGridPane.paddingProperty().set(new Insets(10, 10, 10, 10));
		
		todoGridPane.add(todoCompleteCheckbox, 0, currentRow);
		todoGridPane.add(todoTitle, 1, currentRow);
		todoGridPane.add(deadlineText, 2, currentRow);
		todoGridPane.add(openTodoViewBtn, 4, currentRow);
		
		todoGridPane.setPrefWidth(todoContainer.getWidth());
		todoGridPane.setMinWidth(todoContainer.getWidth());
		
		todoContainer.getChildren().add(todoGridPane);	
	}
	
	private int currentRow = 0;
	
	/**
	 * init the controller iwth a project attached
	 * @param project any valid Project model
	 */
	protected void initWithProject(Project project){
		this.project = project;
	}
	

}
