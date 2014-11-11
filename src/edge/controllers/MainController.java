package edge.controllers;

import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.util.List;

import javax.imageio.ImageIO;

import edge.helper.EdgeSession;
import edge.logic.MainApplication;
import edge.logic.EdgeFxmlLoader;
import edge.models.Project;
import edge.models.Todo;
import edge.models.User;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
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
import javafx.scene.layout.BorderPane;
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
		super();
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
	protected ListView userListView;
	
	@FXML
	protected ListView todosListView;
	
	@FXML
	protected Button createUserButton;
	
	@FXML
	protected Button deleteUserBtn;
	
	@FXML
	protected ImageView avatarImageView;
	
	@FXML
	protected Button editUserBtn;
	
	@FXML
	protected Text createdAtText;
	
	@FXML
	protected Text numberOfProjectsText;
	
	@FXML
	protected Text openTodosText;
	User selectedUser;
	
	@FXML
	public void editUser(){
		User transmittableUser = selectedUser;
		UserController userController = new UserController(transmittableUser);
		userController.setParent(this);
		openView("user_new.fxml", userController);
	}
	
	@FXML
	public void deleteUser(){
		
	}
	
	@FXML
	public void createUser(){
		UserController userController = new UserController();
		userController.setParent(this);
		openView("user_new.fxml", userController);
	}
	
	@FXML
	public void loadTodoinList(){
		
	}
	
	@FXML
	protected Button createUserBtn;
	
	@FXML
	protected TextField firstnameField;
	
	@FXML
	protected TextField lastnameField;
	
	@FXML
	protected TextField emailField;
	
	@FXML
	protected TextField usernameField;
	
	@FXML
	public void updateProjectsGrid(){
		projectsGrid.getChildren().clear();
		drawProjectsGrid();
		//	gridScrollPane.setPrefHeight(projectsGrid.getHeight());
	}
	
	@FXML
	public void createProject(){
		try
		{
			ProjectController projectController = new ProjectController();
			projectController.setParent(this);
			openView("project_new.fxml", projectController);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	@FXML
	private AnchorPane userContentPane;
	
	@FXML
	private ListView<String> settingsList;
	
	@FXML
	private AnchorPane settingsView;
	
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
		
		this._initUserList();
		this._initTodoList();
		this._initSettingsList();
		
		
		this.settingsList.setOnMouseClicked((event) -> {
			String selectedItem = (String) settingsList.getSelectionModel().getSelectedItem();
			String view = selectedItem;
			view = "settings/" + view.toLowerCase() + ".fxml";
			
			Class<?> settingsController;
			try {
				settingsController = (Class) Class.forName("edge.controllers.settings." + selectedItem + "SettingsController");
				
				Scene settingsScene = getSceneWithController(view, settingsController.newInstance());
				Parent s = (Parent) settingsScene.getRoot();
				settingsView.getChildren().add(s);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		
		});
		this.userListView.setOnMouseClicked((event) -> {
			selectedUser = (User) userListView.getSelectionModel().getSelectedItem();
			_updateUserView(selectedUser);
		});
	}
	
	private void _initSettingsList() {
		List<String> settings = new ArrayList<String>(){{
			add("Templates"); // hooks views/settings/templates.fxml
			add("FTP"); // hooks views/settings/ftp.fxml
			add("Email");
		}};
		
		settingsList.getItems().addAll(settings);
		
		
	}

	protected void _initUserList(){
		
		List<User> users = User.getAll();
		users.forEach( (user) -> {
			this.userListView.getItems().add(user);
		});
	}
	
	protected void _initTodoList(){
		List<Todo> todos = EdgeSession.getActiveUser().getTodo();
		if(todos != null){
			todos.forEach( (todo) -> {
				this.todosListView.getItems().add(todo.getTitle());
			});
		}
	}
	
	public void refreshUserList(){
		this.userListView.getItems().clear();
		this._initUserList();
	}
	
	public void refreshTodoList(){
		this.todosListView.getItems().clear();
		this._initTodoList();
	}
	
	private User activeUser;
	private void _updateUserView(User user){
		userContentPane.setOpacity(1.0);
		firstnameField.setText(user.getFirstname());
		lastnameField.setText(user.getLastname());
		emailField.setText(user.getEMail());
		usernameField.setText(user.getUsername());
		
		if (user.getImage() == null){
			ImageView t = new ImageView();
			avatarImageView.setImage(new Image(getNoPicturePath()));
			
		} else {
			avatarImageView.setImage(byteArrayToImage(user.getImage()));
		}
		//numberOfProjectsText.setText("" + user.getProjects().size());
		//openTodosText.setText("" + user.getTodos().size());
		this.activeUser = user;
	}
	
	
	protected void drawProjectsGrid(){
		List<Project> projects = Project.getAll();
		
		
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
			

			
			ImageView imageView = new ImageView();
			if (project.getImage() != null){
				imageView.setImage(byteArrayToImage(project.getImage()));
			} else {
				imageView.setImage(new Image(getNoPicturePath()));
			}
			

			imageView.setFitWidth(160);
			imageView.setFitHeight(160);
			
			imageView.setLayoutX(0.5 * (260 - 156));
			imageView.autosize();

			projectImage.getChildren().add(imageView);
			//projectImage.setPrefWidth(projectBoxSize - 20);

			projectImage.setMaxWidth(260.0);
			
			
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
