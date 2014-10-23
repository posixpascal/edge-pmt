package edge.controllers;

import java.awt.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class LoginController {
	@FXML
	private Button loginBtn;
	
	@FXML
	private void initialize() {
		loginBtn.setOnAction( (event) -> {
			System.out.println("OK");
		});
	}
}
