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
	protected void addErrorClass(TextField field){
		field.getStyleClass().add("error");
	}
	
	public File getNoPictureFile(){
		try {
			return new File(this.getClass().getResource("/edge/assets/img/no-picture.png").toURI());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String getNoPicturePath(){
		return getNoPictureFile().getAbsoluteFile().toURI().toString();
	}
	
	private Object parent = null;
	
	public void setParent(Object parent){
		this.parent = parent;
	}
	
	public Object getParent(){
		return this.parent;
	}
	
	protected void addErrorClass(PasswordField field){
		field.getStyleClass().add("error");
	}

	protected void addSuccessClass(TextField field){
		field.getStyleClass().add("success");
	}
	
	protected void addSuccessClass(PasswordField field){
		field.getStyleClass().add("success");
	}
	
	
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
	
	
	protected File openFileChooser(Stage stage){
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Datei zum Projekt hinzufügen");
		fileChooser.getExtensionFilters().addAll(
			new FileChooser.ExtensionFilter("Alle Dateien", "*.*")
		);
		if (stage == null) stage = MainApplication.getInstance().getRootStage();
		return fileChooser.showOpenDialog(stage);
	}
	
	protected void openView(String view, Object controller){
		EdgeFxmlLoader loader = new EdgeFxmlLoader("../views/" + view);
		loader.getRawLoader().setController(controller);
		
		Stage stage = new Stage();
		Scene scene = null;
		
		try {
			scene = new Scene(
					(Pane) loader.getRawLoader().load()
				);
			stage.setScene(
					scene
				);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		stage.show();
	}
	
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
