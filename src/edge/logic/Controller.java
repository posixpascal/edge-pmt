package edge.logic;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

public class Controller implements Initializable{
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	public void toMainView(ActionEvent event) throws IOException {
		FXMLLoader.load(getClass().getResource("main.fxml"));
	}

	
}
