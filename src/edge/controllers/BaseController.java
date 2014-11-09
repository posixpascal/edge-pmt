package edge.controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;

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
	
	protected File openImageChooser(){
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Bild zum Projekt hinzufügen");
		fileChooser.getExtensionFilters().addAll(
			new FileChooser.ExtensionFilter("Alle Bilder", "*.*"),
			new FileChooser.ExtensionFilter("JPG", "*.jpg"),
			new FileChooser.ExtensionFilter("PNG", "*.png")
		);
		return fileChooser.showOpenDialog(MainApplication.getInstance().getRootStage());
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
	
}
