package edge.controllers;

import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

import javax.imageio.ImageIO;

import edge.logic.MainApplication;
import edge.logic.EdgeFxmlLoader;
import edge.models.Project;
import edge.models.User;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.image.*;
public class MainController extends BaseController {
	public MainController(){
		System.out.println("faggots");
	}
	List<Project> openProjects = new ArrayList<Project>(5);
	protected final int WINDOW_WIDTH = 1306;
	protected final int WINDOW_HEIGHT = 517;
	
	@FXML
	protected ToolBar bottomToolbar;
	
	@FXML
	public TilePane projectsGrid;
	
	@FXML
	protected AnchorPane gridAnchorPane;
	
	@FXML
	protected ScrollPane gridScrollPane;
	
	@FXML
	protected Button createProjectButton;
	
	@FXML
	public void updateProjectsGrid(){
		projectsGrid.getChildren().clear();
		drawProjectsGrid();
	}
	
	@FXML
	public void createProject(){
		try
		{
			ProjectController projectController = new ProjectController();
			projectController.setMainController(this);
			openView("project_new.fxml", projectController);
		
		
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	protected int currentColumnIndex;
	protected int currentRowIndex;
	
	// how many columns for projects should be in the grid yo.
	protected final int maxColumns = 4;
	
	@FXML
	public void initialize(){
		bottomToolbar.setPrefWidth(WINDOW_WIDTH);
		bottomToolbar.setMinWidth(WINDOW_WIDTH);
		gridScrollPane.setPrefWidth(WINDOW_WIDTH - 5);
		gridScrollPane.setMinWidth(WINDOW_WIDTH - 5);

		gridScrollPane.setPrefHeight(550);
		
		gridAnchorPane.setPrefWidth(WINDOW_WIDTH - 30);
		gridAnchorPane.setMinWidth(WINDOW_WIDTH - 30);

		drawProjectsGrid();
	}
	
	protected void drawProjectsGrid(){
		List<Project> projects = Project.getAll();
		
		// 4x4 grid for projects.
		/*int rows = (int) Math.ceil(projects.size() / 4);
				
		for (int i = 0; i < maxColumns; i++){
			projectsGrid.addColumn(i);
		}
				
		for (int i = 0; i < rows; i++){
			projectsGrid.addRow(i);
		}
				
				
				
		double projectBoxSize = 280;
				
<<<<<<< HEAD
		projectsGrid.setPadding(new Insets(200, 10, 10, 10));
=======
		
>>>>>>> branch 'master' of https://edgeVK@bitbucket.org/edgeVK/edge-projektmanagement.git
		
		projectsGrid.setPrefHeight(700 * rows);
		projectsGrid.setMinHeight(700 * rows);
		projectsGrid.setMaxHeight(700 * rows);
		
		
		gridAnchorPane.setPrefHeight(700 * rows);
		gridAnchorPane.setMinHeight(700 * rows);
		gridAnchorPane.setMaxHeight(700 * rows);
		
	
		
	
		currentColumnIndex = 0;
		currentRowIndex = 0;
		*/
		
		projectsGrid.setVgap(50);
		projectsGrid.setHgap(50);
		
		projectsGrid.setPadding(new Insets(100, 10, 10, 10));
		
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
			//projectBox.setMinWidth(projectBoxSize);
			
			
					
			projectBox.setPadding(new Insets(10, 10, 10, 10));
				
			Text projectName = new Text();
			projectName.setText(project.getName());
			projectName.setTextAlignment(TextAlignment.CENTER);
			
			Text customerName = new Text();
			customerName.setText("Kunde: " + project.getCustomerName());
			customerName.setFill(Paint.valueOf("#999999"));
			
			Pane projectImage = new Pane();
			projectImage.setPrefHeight(260);
			

			

			if (project.getImage() != null){
				ImageView imageView = new ImageView();

				ByteArrayInputStream bais = new ByteArrayInputStream(project.getImage());
				BufferedImage bImage = null;
				try {
					bImage = ImageIO.read(bais);
					Image image = SwingFXUtils.toFXImage(bImage, null);
					imageView.setImage(image);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
		
				imageView.setFitWidth(156);
				imageView.setFitHeight(156);
				imageView.setStyle("-fx-border-radius: 50%");
				imageView.setLayoutX(0.5 * (260 - 156));
				imageView.autosize();
	
				projectImage.getChildren().add(imageView);
				//projectImage.setPrefWidth(projectBoxSize - 20);

				projectImage.setMaxWidth(260.0);

			}
			
			
			ProgressBar projectProgress = new ProgressBar();
			
			int totalTodos = project.getTodos().size();
			int closedTodos = project.getClosedTodos().size();
			
			double projectActualProgress = 0.0;
			
			projectProgress.setProgress(projectActualProgress);
			
			projectProgress.setMinWidth(260);		
					
					
					
			projectBox.add(projectName, 0, 0);
			projectBox.add(customerName, 0, 1);
			projectBox.add(projectImage, 0, 2);
			projectBox.add(projectProgress, 0, 3);
					
					
			projectBox.setBackground(
				new Background(
						new BackgroundFill(Color.rgb(255, 255, 255), CornerRadii.EMPTY, Insets.EMPTY)
				)
			);
			

			Long projectId = project.getId();
			
			// TODO: das kann man sicher optimieren.
			projectBox.setOnMouseClicked( (m) -> {
				if (!openProjects.contains(project)){
					
					ProjectViewController projectViewController = new ProjectViewController(project);
					openView("project_view.fxml", projectViewController);
					
					openProjects.add(project); // TODO: das müssen wir rückgängig machen wenns geschlossen wird!
				}
				
			});
					
			projectBox.setStyle("-fx-effect: dropshadow(three-pass-box, #000, 5, 0, 0, 0)");
					
			projectsGrid.getChildren().addAll(projectBox);
			if (currentColumnIndex >= maxColumns){
				currentColumnIndex = 0;
				currentRowIndex++;
			}		
		});
	}
}
