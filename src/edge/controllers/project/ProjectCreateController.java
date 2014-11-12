package edge.controllers.project;




import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import edge.controllers.MainController;
import edge.helper.EdgeError;
import edge.helper.EdgeMailer;
import edge.logic.Database;
import edge.models.Project;
import edge.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ProjectCreateController extends ProjectController {
	public ProjectCreateController(){
		super();
	}
	
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
	private ListView<User> coworkerTable;
	
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
		imageView.setImage(new Image(getNoPicturePath()));

	}
	
	private File imagePath = null;
	
	@FXML
	private void setImage(){
		this.imagePath = openImageChooser((Stage) this.imageView.getScene().getWindow());
		if (this.imagePath  != null){		
			imageView.setImage(new Image(this.imagePath.getAbsoluteFile().toURI().toString()));
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
			byte[] bFile = imageToByteArray(imagePath);
			project.setImage(bFile);
		}
		
		/**
		 * sets the deadline of the project.
		 * we have to transform the localDate to a Date object here.
		 * since toEpochDay returns the number of days until 1.1.1970 (unix timestamp)
		 * we have to transform the days to seconds instead.
		 */
		LocalDate t = deadlineField.getValue();
		long timestamp = t.toEpochDay() * 24 * 60 * 60 * 1000;
		Date deadline = new Date(timestamp);
		project.setDeadline(deadline);
		
		/*
		 *  notify every selected user per mail here
		 */
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
		
			((MainController) this.getParent()).updateProjectsGrid();
			
			Stage stage = (Stage) projectNameField.getScene().getWindow();
			stage.close();
		} 
		else 
		{
			EdgeError.alert("Fehler beim Erstellen", "Das Projekt konnte nicht erstellt werden.");
		}
		
	}
}
