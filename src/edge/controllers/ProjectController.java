package edge.controllers;




import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import edge.helper.EdgeMailer;
import edge.logic.Database;
import edge.logic.EdgeFxmlLoader;
import edge.logic.MainApplication;
import edge.models.Project;
import edge.models.User;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ProjectController extends BaseController {
	@FXML
	private TextField projectNameField;
	
	@FXML
	private Pane projectPane;
	
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
	private ImageView imageView;
	
	@FXML
	private void initialize() {
		

	}
	
	public ProjectController(){}
	private MainController mainController;
	
	
	public void setMainController(MainController mainController){
		this.mainController = mainController;
	}
	
	private File imagePath = null;
	
	@FXML
	private void setImage(){
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Bild zum Projekt hinzufügen");
		fileChooser.getExtensionFilters().addAll(
			new FileChooser.ExtensionFilter("Alle Bilder", "*.*"),
			new FileChooser.ExtensionFilter("JPG", "*.jpg"),
			new FileChooser.ExtensionFilter("PNG", "*.png")
		);
		this.imagePath = fileChooser.showOpenDialog(MainApplication.getInstance().getRootStage());
		if (this.imagePath  != null){		
			ImageView t = new ImageView();
			imageView.setImage(new Image(this.imagePath .getAbsoluteFile().toURI().toString()));
			
		}
		
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
			projectCreateWindow.setNewView(StageTitle, scene); 
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
		
	
		if (imagePath != null){
			byte[] bFile = new byte[(int) imagePath.length()];
			try {
				FileInputStream fis = new FileInputStream(imagePath);
				fis.read(bFile);
				fis.close();
			} catch (IOException e){
				e.printStackTrace();
			}
			
			project.setImage(bFile);
			
		}
		
		LocalDate t = deadlineField.getValue();
		long timestamp = t.toEpochDay() * 24 * 60 * 60 * 1000;
		Date deadline = new Date(timestamp);
		project.setDeadline(deadline);
		
		// notify every selected user per mail here
		if (notifyPerEmailCheckBox.isSelected())
		{
			List<User> users = new ArrayList<User>();
			List<String> targets = new ArrayList<String>();
			
			users.forEach( (user) -> {
				targets.add(user.getEMail());
			});
			
			EdgeMailer mailer = new EdgeMailer(targets);
			mailer.setSubject("EDGE: Sie wurden einem Projekt zugeteilt");
			mailer.setMessage("YoYoYo");
			mailer.sendMail();
		}
		
		if (project.isValid())
		{

			Database.saveAndCommit(project);

			
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Hinweis");
			alert.setHeaderText("Projekt '" + projectName + "' für '" + customerName + "' erstellt.");
			alert.setContentText("Das Projekt wurde erfolgreich erstellt. Die Mitarbeiter wurden per E-Mail informiert.");
			alert.showAndWait();
			Stage stage = (Stage) projectPane.getScene().getWindow();
			
			this.mainController.updateProjectsGrid();
			
			stage.close();
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
