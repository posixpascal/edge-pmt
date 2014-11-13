package edge.controllers.todo;

import edge.helper.EdgeSession;
import edge.models.Project;
import edge.models.Todo;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class TodoViewController extends TodoController {
	@FXML
	private Text deadlineText;
	
	@FXML
	private Text todoText;
	
	@FXML
	private WebView webView;
	
	@FXML
	private ImageView currentUserImage;
	
	@FXML
	private Button writeCommentBtn;
	
	@FXML
	private Button viewCommentsBtn;
	
	@FXML
	private TextArea commentTextArea;
	
	@FXML
	private CheckBox informAdminCheckbox;
	
	@FXML
	private CheckBox isClosedCheckbox;
	
	@FXML
	private void toggleClosedState(){}
	
	@FXML
	private void viewComments(){
		TodoCommentsController todoCommentsCtrl = new TodoCommentsController();
		todoCommentsCtrl.setParent(this);
		openView("view_comments.fxml", todoCommentsCtrl);
	}
	
	@FXML
	private void writeComment(){};
	
	@FXML
	private void initialize(){
		this.todoText.setText(activeTodo.getTitle());
		
		if (activeTodo.getDeadline() != null)
			this.deadlineText.setText("Deadline: " + activeTodo.getDeadline().toString());
		else 
			this.deadlineText.setText("Keine Deadline gesetzt.");
		
		/**
		 * sets the image, if no image is present it uses the default image
		 */
		if (EdgeSession.getActiveUser().getImage() != null){
			this.currentUserImage.setImage(byteArrayToImage(EdgeSession.getActiveUser().getImage()));
		} else {
			this.currentUserImage.setImage(new Image(getNoPicturePath()));
		}
		
		WebEngine webEngine = webView.getEngine();
		webEngine.loadContent(activeTodo.getContent());
		webView.setDisable(true);
		
		
		if (activeTodo.isClosed()){
			isClosedCheckbox.setSelected(true);
		}	
	}
	
	private Todo activeTodo;
	private Project activeProject;
	
	
	public TodoViewController(Todo todo, Project project){
		this.activeTodo = todo;
		this.setActiveProject(project);
		
	}

	/**
	 * gets the active project for the todoView
	 * @return
	 */
	public Project getActiveProject() {
		return activeProject;
	}

	/**
	 * sets the active project for the todoView
	 * @param activeProject
	 */
	public void setActiveProject(Project activeProject) {
		this.activeProject = activeProject;
	}

	
	
}
