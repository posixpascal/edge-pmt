package edge.controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import edge.logic.EdgeFxmlLoader;
import edge.logic.MainApplication;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
public class BaseController {
	/**
	 * Adds error class to a textfield instance
	 * @param field a textfield instance
	 */
	protected void addErrorClass(TextField field){
		field.getStyleClass().add("error");
	}
	
	/**
	 * gets the file object of the NoPicture file (this image is used when a project or user doesn't have an image)
	 * @return File object which points to the no-picture file 
	 */
	public File getNoPictureFile(){
		try {
			return new File(this.getClass().getResource("/edge/assets/img/no-picture.png").toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * gets the no-picture-file absolute path.
	 * @return a string representing the path to the no-picture file
	 */
	public String getNoPicturePath(){
		return getNoPictureFile().getAbsoluteFile().toURI().toString();
	}
	
	private Object parent = null;
	
	/**
	 * sets the parent controller.
	 * this function is used to create a bridge between 2 controller classes.
	 * @param parent Any controller object
	 */
	public void setParent(Object parent){
		this.parent = parent;
	}
	
	/**
	 * returns the parent controller attached to the current class instance
	 * @return the parent controller of the class
	 */
	protected Object getParent(){
		return this.parent;
	}
	
	/**
	 * adds an error class to a password field
	 * @param field any password field object
	 */
	protected void addErrorClass(PasswordField field){
		field.getStyleClass().add("error");
	}

	/**
	 * adds a success class to a textfield
	 * @param field any textfield object
	 */
	protected void addSuccessClass(TextField field){
		field.getStyleClass().add("success");
	}
	
	
	/**
	 * adds a success class to any passwordfield
	 * @param field any passwordfield object
	 */
	protected void addSuccessClass(PasswordField field){
		field.getStyleClass().add("success");
	}
	
	/**
	 * transform a byte array to an image object
	 * This method consumes an image from the DB and transforms it
	 * into a valid javafx.image.Image
	 * @see javafx.image.Image
	 * @param byteArray a file represented as a byte array
	 * @return a javafx.image.Image object
	 */
	protected Image byteArrayToImage(byte[] byteArray){
		ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);
		BufferedImage bImage = null;
		Image image = null;
		
		try {
			bImage = ImageIO.read(bais);
			image = SwingFXUtils.toFXImage(bImage, null);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		return image;
	}
	
	/**
	 * transforms a file to a byteArray
	 * this method is used to store files to the database.
	 * @param imagePath 
	 * @return
	 */
	protected byte[] imageToByteArray(File imagePath){
		byte[] bFile = new byte[(int) imagePath.length()];
		try {
			FileInputStream fis = new FileInputStream(imagePath);
			fis.read(bFile);
			fis.close();
		} catch (IOException e){
			e.printStackTrace();
		}
		return bFile;
	}
	
	
	/**
	 * opens a fileChooser object within the given stage object
	 * adds filter for common image formats.
	 * @param stage any stage instance (defaults to MainApplication Stage)
	 * @return a file when the user selects a file in the filechooser dialog. returns null if no file was selected.
	 */
	protected File openImageChooser(Stage stage){
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Bild zum Projekt hinzufügen");
		fileChooser.getExtensionFilters().addAll(
			new FileChooser.ExtensionFilter("Alle Bilder", "*.*"),
			new FileChooser.ExtensionFilter("JPG", "*.jpg"),
			new FileChooser.ExtensionFilter("PNG", "*.png")
		);
		if (stage == null) stage = MainApplication.getInstance().getRootStage();
		return fileChooser.showOpenDialog(stage);
	}
	
	/**
	 * opens a filechooser object within the given stage
	 * allows any file to be selected.
	 * @param stage any stage instance (defaults to MainApplication Stage)
	 * @return a file when the user selects a file in the filedialog, null if no file was selected.
	 */
	protected File openFileChooser(Stage stage){
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Datei zum Projekt hinzufügen");
		fileChooser.getExtensionFilters().addAll(
			new FileChooser.ExtensionFilter("Alle Dateien", "*.*")
		);
		if (stage == null) stage = MainApplication.getInstance().getRootStage();
		return fileChooser.showOpenDialog(stage);
	}
	
	/**
	 * opens a given <tt>fxml</tt> file and attaches <tt>controller</tt> to it.
	 * @param view a string representing the .fxml filename (you have to omit the full path)
	 * @param controller any controller object which should handle the view
	 */
	protected void openView(String view, Object controller){
		Scene scene = getSceneWithController(view, controller);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
	}
	
	/**
	 * opens a view within the stage of an existing view
	 * @param view a string representing the .fxml filename (you have to omit the full path)
	 * @param controller any controller object which should handle the view
	 * @param stage any stage which will handle the view
	 */
	protected void openView(String view, Object controller, Stage stage){
		Scene scene = getSceneWithController(view, controller);
		stage.setScene(scene);
	}
	
	/** 
	 * returns a scene object from a fxml file with a controller attached
	 * @param view a string representing the .fxml filename (you have to omit the full path)
	 * @param controller any controller object which should handle the view
	 * @return
	 */
	protected Scene getSceneWithController(String view, Object controller){
		EdgeFxmlLoader loader = new EdgeFxmlLoader("../views/" + view);
		loader.getRawLoader().setController(controller);
		Scene scene = null;
		try {
			scene = new Scene(
				(Pane) loader.getRawLoader().load()
			);
		} catch (Exception e){
			e.printStackTrace();
		}
		return scene;
	}
	
	/**
	 * A HashMap mapping file extensions to images. This is used to add preview-symbols for
	 * specific file extensions. The valueString should be a String representing any image filename
	 * located in <tt>/edge/assets/img/mimetypes/</tt>
	 */
	private HashMap<String, String> extImages = new HashMap<String, String>(){{
		put("pdf", "application-pdf.png");
		put("js", "application-javascript.png");
		put("ai", "application-illustrator.png");
		put("rtf", "application-rtf.png");
		put("pptx", "application-vnd.ms-powerpoint.png");
		put("ppt", "application-vnd.ms-powerpoint.png");
		put("xlsx", "application-vnd.ms-excel.png");
		put("xls", "application-vnd.ms-excel.png");
		put("zip", "application-x-7z-compressed.png");
		put("gz", "application-x-7z-compressed.png");
		put("java", "application-x-java.png");
		put("png", "application-x-it87.png");
		put("gif", "application-x-it87.png");
		put("jpeg", "application-x-it87.png");
		put("jpg", "application-x-it87.png");
		put("php", "application-x-php.png");
		put("perl", "application-x-perl.png");
		put("ruby", "application-x-ruby.png");
		put("mp3", "audio-x-flac.png");
		put("ogg", "audio-x-flac.png");
		put("flac", "audio-x-flac.png");
		put("wma", "audio-x-flac.png");
		put("mp4", "audio-vnd.rn.realvideo.png");
		put("mpeg", "audio-vnd.rn.realvideo.png");
		put("mov", "audio-vnd.rn.realvideo.png");
		put("webm", "audio-vnd.rn.realvideo.png");
		put("ogv", "audio-vnd.rn.realvideo.png");
		put("css", "text-css.png");
		put("html", "text-html.png");
		put("csv", "test-csv.png");
		put("undefined", "x-office-document.png");
		put("txt", "x-office-document.png");
	}};
	
	/**
	 * Returns a file pointer for the matching extension.
	 * the extension are mapped in <tt>extImages</tt>
	 * @param fileExtension a string representing the file extension
	 * @see edge.controllers.BaseController.extImages
	 * @return
	 */
	protected File fromFileExtension(String fileExtension){
		fileExtension = fileExtension.toLowerCase();
		if (!this.extImages.containsKey(fileExtension)){
			fileExtension = "undefined";
		}
		try {
			return new File(this.getClass().getResource("/edge/assets/img/mimetypes/" + this.extImages.get(fileExtension)).toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return this.getNoPictureFile();
	
		}
	}
}
