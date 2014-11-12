package edge.controllers;

import java.util.ArrayList;
import java.util.List;

import edge.controllers.project.ProjectCreateController;
import edge.controllers.project.ProjectViewController;
import edge.helper.EdgeError;
import edge.helper.EdgeSession;
import edge.models.Project;
import edge.models.Todo;
import edge.models.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.image.*;

public class MainController extends BaseController {
	public MainController(){
		super();
	}
	
	/**
	 * a list of open projects which are visible on the grid
	 */
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
	protected AnchorPane mainPane;
	
	@FXML
	protected Button createProjectButton;
	
	/**
	 * Warnings suppressed because we can't really predict
	 * Hibernate objects here.
	 */
	@SuppressWarnings("rawtypes")
	@FXML
	protected ListView userListView;
	
	/**
	 * Warnings suppressed because we can't really predict
	 * Hibernate objects here.
	 */
	@SuppressWarnings("rawtypes")
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
	
	/**
	 * the user which is selected in the <tt>Meine Todos</tt> tab.
	 */
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
		if (!EdgeSession.getActiveUser().isAdmin() || !selectedUser.delete()){
			EdgeError.alert("Konnte Benutzer nicht löschen!", "Der Benutzer konnte nicht gelöscht werden. Bitte überprüfen Sie Ihre Rechte.");
		}
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
	}
	
	/**
	 * opens the create project view
	 */
	@FXML
	public void createProject(){
		try
		{
			ProjectCreateController projectController = new ProjectCreateController();
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
	
	/**
	 * initializes the stage.
	 * sets the min width of view components and inits the
	 * projects, user, todo and settings list.
	 * 
	 * also attaches click handlers to the list components.
	 */
	@FXML
	public void initialize(){
		bottomToolbar.setPrefWidth(WINDOW_WIDTH);
		bottomToolbar.setMinWidth(WINDOW_WIDTH);
		
		gridScrollPane.setPrefWidth(WINDOW_WIDTH - 5);
		gridScrollPane.setMinWidth(WINDOW_WIDTH - 5);
		gridScrollPane.setPrefHeight(550);
		
		gridAnchorPane.setPrefWidth(WINDOW_WIDTH - 30);
		gridAnchorPane.setMinWidth(WINDOW_WIDTH - 30);

		/**
		 * populate every list in the view with stuff
		 * from the database
		 */
		drawProjectsGrid();
		this._initUserList();
		this._initTodoList();
		this._initSettingsList();
		
		
		/**
		 * change settings view when listItem was clicked.
		 */
		this.settingsList.setOnMouseClicked((event) -> {
			String selectedItem = (String) settingsList.getSelectionModel().getSelectedItem();
			String view = selectedItem;
			view = "settings/" + view.toLowerCase() + ".fxml";
			
			Class<?> settingsController;
			
			try {
				settingsController = (Class<?>) Class.forName("edge.controllers.settings." + selectedItem + "SettingsController");
				
				Scene settingsScene = getSceneWithController(view, settingsController.newInstance());
				Parent s = (Parent) settingsScene.getRoot();
				settingsView.getChildren().add(s);
			} catch (Exception e) {
				e.printStackTrace();
				EdgeError.alert("Konnte SettingsView nicht laden", "Scheinbar wurde versucht auf eine nicht existierende EinstellungsView zuzugreifen, EDGE PMT hat diesen Vorgang abgebrochen.");
			}
			
		
		});
		
		/**
		 * add listener to userlist when a user was clicked
		 */
		this.userListView.setOnMouseClicked((event) -> {
			selectedUser = (User) userListView.getSelectionModel().getSelectedItem();
			_updateUserView(selectedUser);
		});
	}
	
	/**
	 * inits the settings list.
	 * Settings are added using Strings.
	 * 
	 * The pattern is used like this:
	 * 
	 * TemplateName = Test
	 * ViewFile: "edge/views/settings/test.fxml
	 * Controller: "TestSettingsController.java"
	 *
	 */
	private void _initSettingsList() {
		List<String> settings = new ArrayList<String>(){
			
		private static final long serialVersionUID = -5826923603365080001L;
		
		/**
		 * prefill settingslist
		 */
		{
			add("Templates"); // hooks views/settings/templates.fxml
			add("FTP"); // hooks views/settings/ftp.fxml
			add("Email");
		}};
		
		settingsList.getItems().addAll(settings);
		
		
	}

	/**
	 * inits the user list
	 * warning suppressed because we can't really predict Hibernate
	 * therefore the <tt>List&lt;E&gt;</tt> can't be parameterized.
	 */
	@SuppressWarnings("unchecked")
	protected void _initUserList(){
		List<User> users = User.getAll();
		users.forEach( (user) -> {
			this.userListView.getItems().add(user);
		});
	}
	
	/**
	 * inits the todo list
	 * warning suppressed because we can't really predict Hibernate
	 * therefore the <tt>List&lt;E&gt;</tt> can't be parameterized.
	 */
	@SuppressWarnings("unchecked")
	protected void _initTodoList(){
		List<Todo> todos = EdgeSession.getActiveUser().getTodo();
		if(todos != null){
			todos.forEach( (todo) -> {
				this.todosListView.getItems().add(todo.getTitle());
			});
		}
	}
	
	/**
	 * refreshes the userlist
	 */
	public void refreshUserList(){	
		this.userListView.getItems().clear();
		this._initUserList();
	}
	
	/**
	 * refreshes the todo list
	 */
	public void refreshTodoList(){
		this.todosListView.getItems().clear();
		this._initTodoList();
	}
	
	
	private User activeUser;
	
	/**
	 * updates the user view after a user was eddit.
	 */
	private void _updateUserView(User user){
		userContentPane.setOpacity(1.0);
		firstnameField.setText(user.getFirstname());
		lastnameField.setText(user.getLastname());
		emailField.setText(user.getEMail());
		usernameField.setText(user.getUsername());
		
		/**
		 * sets the user image
		 */
		if (user.getImage() == null){
			avatarImageView.setImage(new Image(getNoPicturePath()));
			
		} else {
			avatarImageView.setImage(byteArrayToImage(user.getImage()));
		}

		this.setActiveUser(user);
	}
	
	
	/**
	 * creates a scrollable tilepane with all projects from the database.
	 * 
	 */
	protected void drawProjectsGrid(){
		List<Project> projects = Project.getAll();
		
		
		/**
		 * sets projectsgrid dimensions
		 */
		projectsGrid.setVgap(50);
		projectsGrid.setHgap(50);
		projectsGrid.setPadding(new Insets(30,30,30,30));
		
		/**
		 * resize listener to update tilePane layout
		 */
		mainPane.widthProperty().addListener(new ChangeListener<Number>() {
		    @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
		    	projectsGrid.resize(newSceneWidth.doubleValue(), 300.);
		    	gridAnchorPane.setMinWidth(newSceneWidth.doubleValue());
		    }
		});
		
		
		projects.forEach( (project) -> {
			/**
			 * inits project container
			 * 
			 * the first row represents the name
			 * the second row represents the customer
			 * the third row represents the image
			 * and the last row represents a neat progressBar.
			 */
			GridPane projectBox = new GridPane();
			projectBox.addColumn(0);
			projectBox.addRow(0); 
			projectBox.addRow(1);
			projectBox.addRow(2); 
			projectBox.addRow(3);
					
			/**
			 * adds size dimensions
			 */
			projectBox.setMaxHeight(300);
			projectBox.setMinHeight(300);
			projectBox.setPrefHeight(300);


			projectBox.setPadding(new Insets(10, 10, 10, 10));
				
			/**
			 * adds name component
			 */
			Text projectName = new Text();
			projectName.setText(project.getName());
			projectName.setTextAlignment(TextAlignment.CENTER);
			
			/**
			 * adds customer name component
			 */
			Text customerName = new Text();
			customerName.setText("Kunde: " + project.getCustomerName());
			customerName.setFill(Paint.valueOf("#999999"));
			
			
			/** 
			 * sets up an image view
			 */
			Pane projectImage = new Pane();
			projectImage.setPrefHeight(260);
			
			ImageView imageView = new ImageView();
			if (project.getImage() != null){
				imageView.setImage(byteArrayToImage(project.getImage()));
			} else {
				imageView.setImage(new Image(getNoPicturePath()));
			}
			
			/** 
			 * sets image dimensions
			 */
			imageView.setFitWidth(160);
			imageView.setFitHeight(160);
			imageView.setLayoutX(0.5 * (260 - 156));
			imageView.autosize();

			projectImage.getChildren().add(imageView);
			projectImage.setMaxWidth(260.0);
		
			/**
			 * adds progress bar of the actual procent of the project
			 */
			ProgressBar projectProgress = new ProgressBar();
			
			//int totalTodos = project.getTodos().size();
			//int closedTodos = project.getClosedTodos().size();
			
			double projectActualProgress = 0.0;
			projectProgress.setProgress(projectActualProgress);
			projectProgress.setMinWidth(260);		
					
			/**
			 * adds view components
			 */
			projectBox.add(projectName, 0, 0);
			projectBox.add(customerName, 0, 1);
			projectBox.add(projectImage, 0, 2);
			projectBox.add(projectProgress, 0, 3);
					
			/** 
			 * style the project box
			 */
			projectBox.setBackground(
				new Background(
						new BackgroundFill(Color.rgb(255, 255, 255), CornerRadii.EMPTY, Insets.EMPTY)
				)
			);
			projectBox.setStyle("-fx-effect: dropshadow(three-pass-box, #000, 5, 0, 0, 0)");
			
			
			/**
			 * adds a click handler to open another view
			 */
			projectBox.setOnMouseClicked( (m) -> {
				if (!openProjects.contains(project)){
					
					ProjectViewController projectViewController = new ProjectViewController(project);
					openView("project_view.fxml", projectViewController);
					
					openProjects.add(project); // TODO: das müssen wir rückgängig machen wenns geschlossen wird!	
				}
			});
					
			/**
			 * adds the project box to the tilePane
			 */
			projectsGrid.getChildren().addAll(projectBox);
		});
	}

	public User getActiveUser() {
		return activeUser;
	}

	public void setActiveUser(User activeUser) {
		this.activeUser = activeUser;
	}
}
