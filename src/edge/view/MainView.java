package edge.view;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainView extends Application{
	private Stage mainStage;
	private String [] args;
	private LoginView lv;
	
	@Override
	public void start(Stage mainStage) throws Exception {
		// TODO Auto-generated method stub
		try{
			lv.close();
			Parent main = (AnchorPane)FXMLLoader.load(getClass().getResource("main.fxml"));
			Scene mainScene = new Scene(main);
			mainScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			mainStage.setScene(mainScene);
			mainStage.show();
			javafx.application.Application.launch();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void toMainView(ActionEvent event) throws IOException {
		lv.close();
		launch(args);
	}
}
