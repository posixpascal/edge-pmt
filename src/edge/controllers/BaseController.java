package edge.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import edge.logic.EdgeFxmlLoader;
import edge.logic.MainApplication;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
public class BaseController {
	protected void addErrorClass(TextField field){
		field.getStyleClass().add("error");
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
	
}
