package edge.controllers;

import java.util.List;

import edge.logic.MainApplication;
import edge.logic.EdgeFxmlLoader;
import edge.models.Project;
import edge.models.User;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class MainController extends BaseController {
	List<Project> projects = Project.getAll();
	
	private final int WINDOW_WIDTH = 1306;
	private final int WINDOW_HEIGHT = 517;
	
	@FXML
	private ToolBar bottomToolbar;
	
	@FXML
	private GridPane projectsGrid;
	
	@FXML
	private AnchorPane gridAnchorPane;
	
	@FXML
	private ScrollPane gridScrollPane;
	
	@FXML
	private Button createProjectButton;
	
	@FXML
	private void updateProjectsGrid(){
		
		
	}
	
	@FXML
	public void createNewStage(){
		try
		{
			EdgeFxmlLoader loader = new EdgeFxmlLoader();
	        Parent projectCreate = (Parent) loader.load("../views/project_new.fxml", ProjectController.class);
	        Scene scene = new Scene(projectCreate, 425, 625);
	        scene.getStylesheets().add(this.getClass().getResource("../assets/stylesheets/project.css").toString());
	        String StageTitle = "Create a new Project";
	       
	       
			MainApplication projectCreateWindow = new MainApplication();
			projectCreateWindow.setNewView(StageTitle, scene);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	private int currentColumnIndex;
	private int currentRowIndex;
	
	// how many columns for projects should be in the grid yo.
	private final int maxColumns = 4;
	
	@FXML
	public void initialize(){
		bottomToolbar.setPrefWidth(WINDOW_WIDTH);
		bottomToolbar.setMinWidth(WINDOW_WIDTH - 10);
		gridScrollPane.setPrefWidth(WINDOW_WIDTH - 10);
		gridScrollPane.setMinWidth(WINDOW_WIDTH - 10);

		gridScrollPane.setPrefHeight(550);
		
		gridAnchorPane.setPrefWidth(WINDOW_WIDTH - 20);
		gridAnchorPane.setMinWidth(WINDOW_WIDTH - 20);

		drawProjectsGrid();
	}
	
	private void drawProjectsGrid(){
		// 4x4 grid for projects.
		int rows = (int) Math.ceil(projects.size() / 4);
				
		for (int i = 0; i < maxColumns; i++){
			projectsGrid.addColumn(i);
		}
				
		for (int i = 0; i < rows; i++){
			projectsGrid.addRow(i);
		}
				
				
				
		double projectBoxSize = 280;
				
		projectsGrid.setPadding(new Insets(10, 10, 10, 10));
		
		projectsGrid.setPrefHeight(700 * rows);
		projectsGrid.setMinHeight(700 * rows);
		projectsGrid.setMaxHeight(700 * rows);
		
		
		gridAnchorPane.setPrefHeight(700 * rows);
		gridAnchorPane.setMinHeight(700 * rows);
		gridAnchorPane.setMaxHeight(700 * rows);
		
		projectsGrid.setVgap(50);
		projectsGrid.setHgap(50);
		
	
		currentColumnIndex = 0;
		currentRowIndex = 0;
		projects.forEach( (project) -> {
			GridPane projectBox = new GridPane();
			projectBox.addColumn(0);
			projectBox.addRow(0); // title
			projectBox.addRow(1); // kunde
			projectBox.addRow(2); // stuff I dont know yet, maybe a pic
			projectBox.addRow(3); // processbar
					
			projectBox.setMaxHeight(300);
			projectBox.setMinHeight(300);
			projectBox.setPrefHeight(300);
			projectBox.setMinWidth(projectBoxSize);
			
			
					
			projectBox.setPadding(new Insets(10, 10, 10, 10));
				
			Text projectName = new Text();
			projectName.setText(project.getName());
			projectName.setTextAlignment(TextAlignment.CENTER);
			
			Text customerName = new Text();
			customerName.setText("Kunde: " + project.getCustomerName());
			customerName.setFill(Paint.valueOf("#999999"));
					
			Pane projectImage = new Pane();
			projectImage.setPrefHeight(260);
			
			ProgressBar projectProgress = new ProgressBar();
			projectProgress.setProgress(0.2);
			projectProgress.setMinWidth(projectBoxSize - 20);		
					
					
					
			projectBox.add(projectName, 0, 0);
			projectBox.add(customerName, 0, 1);
			projectBox.add(projectImage, 0, 2);
			projectBox.add(projectProgress, 0, 3);
					
					
			projectBox.setBackground(
				new Background(
						new BackgroundFill(Color.rgb(255, 255, 255), CornerRadii.EMPTY, Insets.EMPTY)
				)
			);
					
			projectBox.setStyle("-fx-effect: dropshadow(three-pass-box, #000, 5, 0, 0, 0)");
					
			projectsGrid.add(projectBox, currentColumnIndex++, currentRowIndex);
			if (currentColumnIndex >= maxColumns){
				currentColumnIndex = 0;
				currentRowIndex++;
			}
			

				
					
		});
	}
}
